package br.com.starwars.dao;

import com.mongodb.MongoClient;

import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public abstract class EntityDAO {
	
	protected MongoClient cliente = new MongoClient();
    
	protected Morphia morphia = new Morphia();
    
	protected Datastore ds  = morphia.createDatastore(cliente, "db_sysapi");

	public Datastore getDs() {
		return ds;
	}
    
    
}
