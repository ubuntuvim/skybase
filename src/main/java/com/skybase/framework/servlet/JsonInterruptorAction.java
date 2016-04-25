package com.skybase.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skybase.framework.adapter.Adapter;
import com.skybase.framework.adapter.AdapterManager;
import com.skybase.framework.adapter.AdapterMapping;
import com.skybase.framework.adapter.AdapterMappingManager;
import com.skybase.framework.authority.CheckRequestAuthorization;
import com.skybase.framework.warpper.HttpRequestWarpper;
import com.skybase.util.LogUtil;
import com.skybase.util.SkyBaseUtils;

/**
 * Servlet implementation class JsonInterruptorAction
 */
public class JsonInterruptorAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	private Map<String, Object> container = null;
	private String[] cfgFiles = null;

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
		LogUtil.info("========> 处理json请求 <========");
		Map<String, Object> retJson = new HashMap<String, Object>(); 
		PrintWriter out = response.getWriter();
		//请求权限判断
		CheckRequestAuthorization cra = new CheckRequestAuthorization();
		if (!cra.check(request)) {
			retJson.put("flag", false);
			retJson.put("msg", "此请求无权限。");
			retJson.put("code", "403");
			
			out.print(SkyBaseUtils.mapToJson(retJson));
		}
		HttpRequestWarpper hrw = new HttpRequestWarpper();
		//  TODO
		hrw.requestWarpper(request);
		
		try {
			AdapterMappingManager amm = new AdapterMappingManager(this.cfgFiles);
			Map<String, AdapterMapping> adms = amm.getAllAdapterMapping();
			for (Entry<String, AdapterMapping> entry : adms.entrySet()) {
//				String key = entry.getKey();  //key与className是同一个东西
				AdapterMapping am = entry.getValue();
				String className = am.getAdapterClassName();
//				String adapterName = am.getAdapterName();
				//  每个请求都创建一个实例？还是都使用一个实例？
				Adapter adapter = AdapterManager.createAdapter(className);
				adapter.adapter(request, response);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("读取adapter配置文件出错，请确认配置的文件的路径！");
		}
		
		
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//  读取action的配置信息
		String adapterConfigs = config.getInitParameter("adapterConfigFiles");
		SkyBaseUtils.checkConfigFiles(adapterConfigs, "adapter");
		this.cfgFiles = adapterConfigs.replaceAll("\\s*", "").split(",");
	}

}
