package com.skybase.framework.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skybase.framework.container.ClassPathXmlApplicationContext;
import com.skybase.util.LogUtil;
import com.skybase.util.SkyBaseUtils;

/**
 * Servlet implementation class JsonInterruptorAction
 */
public class JsonInterruptorAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> container = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//  读取action的配置信息
		String adapterConfigs = config.getInitParameter("adapterConfigFiles");
		SkyBaseUtils.checkConfigFiles(adapterConfigs, "adapter");

		try {
			//  先去除分割配置文件间的空格（如果有）再转成数组
			String[] cfgArr = adapterConfigs.replaceAll("\\s*", "").split(",");
			ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(cfgArr);
			this.container = cxt.initContainer();
//			new AdapterMappingManager(adapterConfigs.replaceAll("\\s*", "").split(",")).instanceAdapter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtil.info("####### 适配器加载完成  #########");
	}

}
