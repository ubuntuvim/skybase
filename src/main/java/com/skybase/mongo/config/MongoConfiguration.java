package com.skybase.mongo.config;

import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 初始化数据库配置
 * @author ubuntuvim
 */
public class MongoConfiguration extends Configuration {
	
	@Valid
	@NotNull
	public MongoDBConnectorConfiguration mongo = new MongoDBConnectorConfiguration();
}
