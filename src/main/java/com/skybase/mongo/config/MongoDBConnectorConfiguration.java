package com.skybase.mongo.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 数据库配置实体
 * @author ubuntuvim
 */
public class MongoDBConnectorConfiguration {
	
	@NotNull
	public String host;
	
	@Min(1)
	@Max(65535)
	public int port;
	
	@NotNull
	public String db;
	
	@NotNull
	public String user;
	
	@NotNull
	public String password;
}
