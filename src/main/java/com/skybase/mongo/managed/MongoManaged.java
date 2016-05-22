package com.skybase.mongo.managed;

import io.dropwizard.lifecycle.Managed;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.skybase.mongo.config.MongoDBConnectorConfiguration;

/**
 * 链接mongodb
 * @author ubuntuvim
 */
public class MongoManaged implements Managed {
	
	private final MongoClient mongoClient;
	private final Datastore datastore;

	public MongoManaged(MongoDBConnectorConfiguration mongo) throws UnknownHostException {
		this.mongoClient = new MongoClient(mongo.host, mongo.port);
		this.datastore = new Morphia().createDatastore(this.mongoClient, mongo.db);
	}

	public Datastore getDatastore() {
		return this.datastore;
	}

	@Override
	public void start() throws Exception {
		System.out.println("\n================= mongodb数据库连接成功 ==================");
	}

	@Override
	public void stop() throws Exception {
		this.mongoClient.close();
	}
	
	

}
