package br.com.starwars.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.bson.types.ObjectId;

import br.com.starwars.model.Planeta;
import xyz.morphia.query.Query;

@Stateless
public class PlanetaDAO extends EntityDAO {
	
	public void salvaPlaneta( Planeta planeta ) 
	{
		this.ds.save( planeta );

	}
	
	public Planeta buscaPlanetaPorNome( String nomePlaneta ) 
	{
		Query<Planeta> query = this.ds.createQuery( Planeta.class ).filter( "nome = ", nomePlaneta );

		List<Planeta> listaPlanetas = query.asList( );
		
		if( listaPlanetas.size( ) > 0 )
			return listaPlanetas.get( 0 );
		else
			return null;
		
	}

	public Planeta buscaPlanetaPorId( String id ) 
	{	
		if( ObjectId.isValid(id) ) 
		{ 
			Planeta planeta = this.ds.get( Planeta.class, new ObjectId( id ) );

			return planeta;
		}
		else 
		{
			return null;
		}
		
	}

	public List<Planeta> listaTodosPlanetas(  ) 
	{
		Query<Planeta> query = this.ds.createQuery( Planeta.class );

		List<Planeta> listaPlanetas = query.asList( );
		
		return listaPlanetas;
	}

	public boolean removePlanetaPorId( String id ) 
	{
       Planeta planeta = buscaPlanetaPorNome( id );
		
		if( planeta != null ) 
		{
			this.ds.delete( Planeta.class, planeta.getId( ) );
			return true;
		}
           return false;
	}	

	public boolean removePlanetaPorNome( String nomePlaneta ) 
	{
		Planeta planeta = buscaPlanetaPorNome( nomePlaneta );
		
		if( planeta != null ) 
		{
			this.ds.delete( Planeta.class, planeta.getId( ) );
			
			return true;
		}
           return false;
	}

}
