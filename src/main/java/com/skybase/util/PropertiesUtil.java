package com.skybase.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

/**
 * 读取properties配置文件工具类
 * @author ubuntuvim
 */
public class PropertiesUtil {
	private static String propFilePath = "/config.properties";
	
	public static String getValueByKey(String key) {
		Properties prop = new Properties();
		try {
			//  部署tomcat时候到读组方式
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFilePath);
			//本地直接执行main方法到读取方式
//			InputSteam in = PropertiesUtil.class.getClass().getResourceAsStream(propFilePath);
			assertNotNull("加载配置文件["+propFilePath+"]失败！");
			prop.load(in);
			
			return prop.getProperty(key);
		} catch (IOException e) {
			LogUtil.error("读取配置文件${0}出错！\n"+e.getMessage(), propFilePath);
		}
		
		return "";
	}
}
