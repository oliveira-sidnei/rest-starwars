package br.com.starwars.model;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Transient;

@Entity("planetas")
public class Planeta {
	
	@Id
	@JsonIgnore
	private ObjectId id;
	private String nome;
	private String clima;
	private String terreno;
	@Transient
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
	
	@JsonIgnore
	public ObjectId getId() {
		return id;
	}
	public String toJson() {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(new Gson().toJson(this));
		
		return gson.toJson(je);
	}
	public int getQuantidadeFilmes() {
		return quantidadeFilmes;
	}
	public void setQuantidadeFilmes(int quantidadeFilmes) {
		this.quantidadeFilmes = quantidadeFilmes;
	}

	
	
}
