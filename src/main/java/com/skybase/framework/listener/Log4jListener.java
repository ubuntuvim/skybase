package com.skybase.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.skybase.util.PropertiesUtil;

/**
 * 设置log4j日志保存到文件目录
 * @author ubuntuvim
 */
public class Log4jListener implements ServletContextListener {

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.getProperties().remove(PropertiesUtil.getValueByKey("log4jLocalDirName"));
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String tmp = arg0.getServletContext().getRealPath("/");
		System.setProperty(PropertiesUtil.getValueByKey("log4jLocalDirName"), tmp);
	}
	
}
