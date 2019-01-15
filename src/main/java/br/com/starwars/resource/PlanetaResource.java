package br.com.starwars.resource;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
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
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import br.com.starwars.controller.ControladorPlaneta;
import br.com.starwars.dao.UsuarioDAO;
import br.com.starwars.model.Planeta;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("planetas")
public class PlanetaResource {
	
	@EJB
	ControladorPlaneta controladorPlaneta;
	
	@Path("buscaPorNome/{nome}")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response buscaPlanetaPorNome( @PathParam( "nome" )String nomePlaneta )
	{
		try 
		{
			Planeta planeta = controladorPlaneta.buscaPlanetaPorNome( nomePlaneta ); 
			try 
			{
				planeta.setQuantidadeFilmes( controladorPlaneta.buscaQuantidadeFilmes( nomePlaneta ) );
			} catch (IOException e) {
				planeta.setQuantidadeFilmes( 0 );
			}

			return Response.ok( planeta.toJson( ), MediaType.APPLICATION_JSON ).build( );
		} 
		catch ( NullPointerException e ) 
		{
			return Response.status( Status.NOT_FOUND ).entity( "Registro não encontrado." ).build( );

		}
	}
	
	@Path("buscaPorId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaPlanetaPorId( @PathParam("id") String id ) 
	{	
		try 
		{
			Planeta planeta = controladorPlaneta.buscaPlanetaPorId( id );

			try 
			{
				planeta.setQuantidadeFilmes(controladorPlaneta.buscaQuantidadeFilmes( planeta.getNome( ) ) );
			} 
			catch ( IOException e ) 
			{
				planeta.setQuantidadeFilmes(0);
			}

			return Response.ok(planeta.toJson(), MediaType.APPLICATION_JSON).build();
		} 
		catch ( NullPointerException e ) 
		{
			return Response.status( Status.NOT_FOUND ).entity( "Registro não encontrado." ).build( );

		}
	}
		 
		@Path("/lista")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String listaPlanetas( ) {
			
			List<Planeta> listaPlanetas = controladorPlaneta.listaTodosPlanetas( );
			
			StringBuilder sb = new StringBuilder( );
			
			for( Planeta planeta : listaPlanetas ) 
			{
				try 
				{
					planeta.setQuantidadeFilmes( controladorPlaneta.buscaQuantidadeFilmes( planeta.getNome( ) ) );
				} 
				catch ( IOException | IndexOutOfBoundsException ibe ) 
				{
					planeta.setQuantidadeFilmes( 0 );
				}
				sb.append( planeta.toJson( ) );
				sb.append( "\n" );
			}
			
			return sb.toString( );

		
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)	
    public Response adiciona( String conteudo, @HeaderParam("username") String username, 
    		@HeaderParam("password") String password ) {
		
		boolean status = false;
		
		Planeta planeta = new Gson( ).fromJson( conteudo, Planeta.class );
		
        URI uri = URI.create("/planetas/" + planeta.getId( ) );

		 status = UsuarioDAO.validar( username, password );
		 
		 if( !status )
			 return Response.status( Response.Status.UNAUTHORIZED ).entity( "Não autorizado." ).build( );
		
		Planeta planetaBuscado = controladorPlaneta.buscaPlanetaPorNome( planeta.getNome( ) );
		
		if( planetaBuscado != null ) 
		{
			return Response.status( Response.Status.CONFLICT ).entity( "Planeta já cadastrado." ).build( );
			
		}
		
			javax.json.JsonObject jsonObj = criaToken( );
			
		    controladorPlaneta.salvaPlaneta( planeta );
		    
			return Response.created( uri ).entity( jsonObj ).build( );        
    }

	private javax.json.JsonObject criaToken() {
		
		String KEY = "chave";
		
		long tempo = System.currentTimeMillis();
		
		String jwt = Jwts.builder( ).signWith( SignatureAlgorithm.HS256, KEY ).setSubject( "Authentication" )
				.setIssuedAt( new Date( tempo ) ).setExpiration( new Date ( tempo+900000 ) )
				.claim( "email", "sidneiohs@gmail.com" ).compact( );
		
		javax.json.JsonObject jsonObj = Json.createObjectBuilder().add("JWT", jwt).build();
		return jsonObj;
	}
	
	
	@Path("removePorId/{id}")
    @DELETE
    public Response removePlanetaPorId( @PathParam("id") String id, @HeaderParam("username") String username,
    		@HeaderParam("password") String password ) 
	{
		boolean status = UsuarioDAO.validar( username, password );
		
		if( status ) 
		{
			javax.json.JsonObject jsonObj = criaToken( );		
			
				if( !controladorPlaneta.removePlanetaPorId( id ) ) 
				{
			    return Response.status( Response.Status.NOT_FOUND ).entity( "Registro não encontrado." ).build( );
				}
			
			
			return Response.ok( ).entity( jsonObj ).build( );
		}
       
		   return Response.status( Response.Status.UNAUTHORIZED ).entity( "Não autorizado." ).build( );
    }
	
	@Path("removePorNome/{nomePlaneta}")
    @DELETE
    public Response removePlanetaPorNome(@PathParam("nomePlaneta") String nomePlaneta, @HeaderParam("username") String username,
    		@HeaderParam("password") String password) 
	{
		boolean status = UsuarioDAO.validar( username, password );
		
		if( status ) 
		{
			javax.json.JsonObject jsonObj = criaToken( );
			
				if( !controladorPlaneta.removePlanetaPorNome( nomePlaneta ) ) {
			    return Response.status( Response.Status.NOT_FOUND ).entity( "Registro não encontrado." ).build( );
				}
			
			return Response.ok( ).entity( jsonObj ).build( );
		}
       
        return Response.status( Response.Status.UNAUTHORIZED ).entity( "Não autorizado." ).build( );
    }
}
