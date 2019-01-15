package br.com.starwars.model;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;

import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Transient;

@Entity("planetas")
public class Planeta {
	
	@Id
	private ObjectId id;
	@Expose
	private String nome;
	@Expose
	private String clima;
	@Expose
	private String terreno;
	@Transient
	@Expose
	private int quantidadeFilmes;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getClima() {
		return clima;
	}
	
	public void setClima(String clima) {
		this.clima = clima;
	}
	
	public String getTerreno() {
		return terreno;
	}
	
	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public String toJson() {
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(gson.toJson(this));
		
		return gson.toJson(je);
	}
	public int getQuantidadeFilmes() {
		return quantidadeFilmes;
	}
	public void setQuantidadeFilmes(int quantidadeFilmes) {
		this.quantidadeFilmes = quantidadeFilmes;
	}

	
	
}
