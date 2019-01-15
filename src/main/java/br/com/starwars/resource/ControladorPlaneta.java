package br.com.starwars.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.starwars.dao.PlanetaDAO;
import br.com.starwars.model.Planeta;


public class ControladorPlaneta {

	
	 PlanetaDAO daoPlaneta;


	public Planeta buscaPlanetaPorNome(String nomePlaneta) {

		Planeta planeta = daoPlaneta.buscaPlanetaPorNome(nomePlaneta);
		
		return planeta;
	}


	public List<Planeta> listaTodosPlanetas() {

		List<Planeta> listaPlanetas = daoPlaneta.listaTodosPlanetas();
		
		return listaPlanetas;
	}


	public void salvaPlaneta(Planeta planeta) {
		
		daoPlaneta.salvaPlaneta(planeta);
		
	}


	public void removePlaneta(String id) {
		
		daoPlaneta.removePlaneta(id);
	}


	public int buscaQuantidadeFilmes(String planeta) throws IOException, IndexOutOfBoundsException {
		
		URL url = new URL("https://swapi.co/api/planets/?format=json&search=" + planeta);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		urlConnection.connect();
		InputStream is = urlConnection.getInputStream();
		StringWriter sw = new StringWriter();
		String encoding = StandardCharsets.UTF_8.name();
		IOUtils.copy(is, sw, encoding);

		JsonObject jsonObject = (JsonObject) new JsonParser().parse(sw.toString());
	    JsonArray jsonArray = (JsonArray) jsonObject.get("results");
	    JsonObject planetaJson = null;
		try {
			planetaJson = (JsonObject) jsonArray.get(0);
		} catch (IndexOutOfBoundsException e) {
			
			e.printStackTrace();
			return 0;
		}
	    JsonArray planetasArray = (JsonArray) planetaJson.get("films");

	    return planetasArray.size();
	}


	public Planeta buscaPlanetaPorId(String id) {
		
		Planeta planeta = daoPlaneta.buscaPlanetaPorId(id);
		
		return planeta;
	}

}