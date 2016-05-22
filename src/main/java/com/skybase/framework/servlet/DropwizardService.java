package com.skybase.framework.servlet;


import static io.katharsis.rs.KatharsisProperties.RESOURCE_DEFAULT_DOMAIN;
import static io.katharsis.rs.KatharsisProperties.RESOURCE_SEARCH_PACKAGE;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.katharsis.locator.JsonServiceLocator;
import io.katharsis.queryParams.DefaultQueryParamsParser;
import io.katharsis.queryParams.QueryParamsBuilder;
import io.katharsis.rs.KatharsisFeature;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.skybase.domain.repository.ProjectRepository;
import com.skybase.domain.repository.TaskRepository;
import com.skybase.domain.repository.TaskToProjectRepository;
import com.skybase.mongo.config.MongoConfiguration;
import com.skybase.mongo.managed.MongoManaged;

public class DropwizardService extends Application<MongoConfiguration> {

    private GuiceBundle<MongoConfiguration> guiceBundle;

    @Override
    public void initialize(Bootstrap<MongoConfiguration> bootstrap) {

        this.guiceBundle = GuiceBundle.<MongoConfiguration>newBuilder().addModule(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ProjectRepository.class);
                bind(TaskRepository.class);
                bind(TaskToProjectRepository.class);
            }
            @Provides
            public MongoManaged mongoManaged(MongoConfiguration configuration) throws Exception {
                return new MongoManaged(configuration.mongo);
            }
        })
        .setConfigClass(MongoConfiguration.class)
        .build();

        bootstrap.addBundle(this.guiceBundle);
    }	
	
	@Override
	public void run(MongoConfiguration dconfig, Environment env)
			throws Exception {
		
		env.lifecycle().manage((Managed) this.guiceBundle.getInjector().getInstance(MongoManaged.class));
		
		env.jersey().property(RESOURCE_SEARCH_PACKAGE, "com.ubuntuvim.katharsis.domain");
		env.jersey().property(RESOURCE_DEFAULT_DOMAIN, "http://localhost:8080");
		
		KatharsisFeature katharsisFeature = new KatharsisFeature(env.getObjectMapper(), 
				new QueryParamsBuilder(new DefaultQueryParamsParser()), 
				new JsonServiceLocator()   {
			
			@Override
			public <T> T getInstance(Class<T> arg0) {
				return guiceBundle.getInjector().getInstance(arg0);
			}
		});
		
		env.jersey().register(katharsisFeature);
		
	}

	
	public static void main(String[] args) throws Exception {
		new DropwizardService().run(args);
	}
	
}
