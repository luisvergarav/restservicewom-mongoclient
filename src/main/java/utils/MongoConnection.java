package utils;

import static java.lang.String.format;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;

@Singleton
@Startup
public class MongoConnection {

	@Inject
	Logger logger;

	private MongoClient mongo = null;
	private Datastore dataStore = null;
	private Morphia morphia = null;

	

	public MongoClient getMongo() throws RuntimeException {
		if (mongo == null) {
			logger.log(Level.INFO,"Starting Mongo");
			MongoClientOptions.Builder options = MongoClientOptions.builder().connectionsPerHost(4)
					.maxConnectionIdleTime((60 * 1_000)).maxConnectionLifeTime((120 * 1_000));
			;
			

			MongoClientURI uri = new MongoClientURI(System.getenv("MongoUri"), options);
			//MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/test", options);

			
			
			logger.log(Level.INFO,"About to connect to MongoDB @ " + uri.toString());

			try {
				mongo = new MongoClient(uri);
				// mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);

				 MongoDatabase database = mongo.getDatabase(System.getenv("MongoDB"));
			} catch (MongoException ex) {
				logger.log(Level.SEVERE,"An error occoured when connecting to MongoDB", ex);
			} catch (Exception ex) {
				logger.log(Level.SEVERE,"An error occoured when connecting to MongoDB", ex);
			}

			// To be able to wait for confirmation after writing on the DB
			// mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		}

		return mongo;
	}

	public Morphia getMorphia() {
		if (morphia == null) {
			logger.log(Level.INFO,"Starting Morphia");
			morphia = new Morphia();

			logger.log(Level.INFO,format("Mapping packages for clases within %s", "domain.model.entities"));
			morphia.mapPackage("domain.model.entities");
		}

		return morphia;
	}

	@Produces
	@Named("ds") 
	public Datastore getDatastore() {
		if (dataStore == null) {
			String dbName = "testdb";
			logger.log(Level.INFO,format("Starting DataStore on DB: %s", dbName));
			dataStore = getMorphia().createDatastore(getMongo(), dbName);
			return dataStore;
		}

		return dataStore;
	}

	@PostConstruct
	private void iniciar() {
		logger.log(Level.INFO,"Bootstraping");
		getMongo();
		getMorphia();
		getDatastore();
	}

	@PreDestroy
	public void close() {
		logger.info("Closing MongoDB connection");
		if (mongo != null) {
			try {
				mongo.close();
				logger.log(Level.INFO,"Nulling the connection dependency objects");
				mongo = null;
				morphia = null;
				dataStore = null;
			} catch (Exception e) {
				logger.log(Level.INFO,format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
			}
		} else {
			logger.log(Level.INFO,"mongo object was null, wouldn't close connection");
		}
	}

}
