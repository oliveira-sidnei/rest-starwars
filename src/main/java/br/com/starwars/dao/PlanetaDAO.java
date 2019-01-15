package br.com.starwars.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.bson.types.ObjectId;

import br.com.starwars.model.Planeta;
import xyz.morphia.query.Query;


public class PlanetaDAO extends EntityDAO {
	
	public void salvaPlaneta(Planeta planeta) {
		this.ds.save(planeta);

	}
	
	public Planeta buscaPlanetaPorNome(String nomePlaneta) {
		
		Query<Planeta> query = this.ds.createQuery(Planeta.class).filter("nome = ", nomePlaneta);

		List<Planeta> listaPlanetas = query.asList();
		
		return listaPlanetas.get(0);

		
	}

	public Planeta buscaPlanetaPorId(String id) {
		
       Planeta planeta = this.ds.get(Planeta.class, new ObjectId(id));
       
		return planeta;
	}

	public void removePlaneta(String planetaId) {
		
            this.ds.delete(Planeta.class, new ObjectId(planetaId));
   
	}

	public List<Planeta> listaTodosPlanetas() {
		Query<Planeta> query = this.ds.createQuery(Planeta.class);

		List<Planeta> listaPlanetas = query.asList();
		
		System.out.println(listaPlanetas.size());
		
		
		return listaPlanetas;
	}

}
