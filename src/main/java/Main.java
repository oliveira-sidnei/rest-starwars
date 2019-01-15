import com.mongodb.MongoClient;

import br.com.starwars.model.Planeta;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class Main {

	public static void main(String[] args) {
	
		 MongoClient cliente = new MongoClient();
	    
		 Morphia morphia = new Morphia();
	    
		 Datastore ds  = morphia.createDatastore(cliente, "db_sysapi");

		Planeta planeta = new Planeta();
		planeta.setNome("Alderaan");
		planeta.setClima("Good");
		planeta.setTerreno("Forests");
		ds.save(planeta);
		cliente.close();
		
	}

}
