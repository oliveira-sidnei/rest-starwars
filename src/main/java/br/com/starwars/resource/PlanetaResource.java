package br.com.starwars.resource;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.starwars.dao.UsuarioDAO;
import br.com.starwars.model.Planeta;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("planetas")
public class PlanetaResource {
	
	ControladorPlaneta controladorPlaneta;
	
	@Path("buscaPorNome/{nome}")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public String buscaPlanetaPorNome(@PathParam("nome")String nomePlaneta){

		 Planeta planeta = controladorPlaneta.buscaPlanetaPorNome(nomePlaneta);
		 
		try {
			planeta.setQuantidadeFilmes(controladorPlaneta.buscaQuantidadeFilmes(nomePlaneta));
		} catch (IOException e) {
			planeta.setQuantidadeFilmes(0);
			e.printStackTrace();
		}
		 
		 return planeta.toJson();
	}
	
	@Path("buscaPorId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String buscaPlanetaPorId(@PathParam("id") String id) {
		
		Planeta planeta = controladorPlaneta.buscaPlanetaPorId(id);
		
		return planeta.toJson();
	}
		 
		@Path("/lista")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String listaPlanetas() {
			List<Planeta> listaPlanetas = controladorPlaneta.listaTodosPlanetas();
			StringBuilder sb = new StringBuilder();
			
			for(Planeta planeta : listaPlanetas) {
				try {
					planeta.setQuantidadeFilmes(controladorPlaneta.buscaQuantidadeFilmes(planeta.getNome()));
				} catch (IOException | IndexOutOfBoundsException ibe) {
					planeta.setQuantidadeFilmes(0);
					ibe.printStackTrace();
				}
				sb.append(planeta.toJson());
				sb.append("\n");
			}
			return sb.toString();

		
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)	
    public Response adiciona(String conteudo, @HeaderParam("username") String username, 
    		@HeaderParam("password") String password) {
		
		
		Planeta planeta = new Gson().fromJson(conteudo, Planeta.class);
		
        URI uri = URI.create("/planetas/" + planeta.getId());

		boolean status = UsuarioDAO.validar(username, password);
		
		if(status) {
			
			javax.json.JsonObject jsonObj = criaToken();
			
		    controladorPlaneta.salvaPlaneta(planeta);
		    
			return Response.created(uri).entity(jsonObj).build();
		}
		
    
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

	private javax.json.JsonObject criaToken() {
		
		String KEY = "chave";
		
		long tempo = System.currentTimeMillis();
		
		String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, KEY).setSubject("Authentication").setIssuedAt(new Date(tempo)).setExpiration(new Date(tempo+900000)).claim("email", "sidneiohs@gmail.com").compact();
		
		javax.json.JsonObject jsonObj = Json.createObjectBuilder().add("JWT", jwt).build();
		return jsonObj;
	}
	
	
	@Path("remove/{id}")
    @DELETE
    public Response removePlaneta(@PathParam("id") String id, @HeaderParam("username") String username,
    		@HeaderParam("password") String password) {
		
		boolean status = UsuarioDAO.validar(username, password);
		
		if(status) {
			javax.json.JsonObject jsonObj = criaToken();
			
			controladorPlaneta.removePlaneta(id);
			
			return Response.ok().entity(jsonObj).build();
		}
       
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
