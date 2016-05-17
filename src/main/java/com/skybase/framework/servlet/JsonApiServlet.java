package com.skybase.framework.servlet;

import io.katharsis.invoker.KatharsisInvokerBuilder;
import io.katharsis.locator.SampleJsonServiceLocator;
import io.katharsis.servlet.AbstractKatharsisServlet;
import io.katharsis.servlet.KatharsisProperties;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skybase.util.LogUtil;

/**
 * Servlet implementation class JsonApiServlet
 */
public class JsonApiServlet extends AbstractKatharsisServlet {
	private static final long serialVersionUID = 1L;
    
	//  katharsis转换框架所需变量
	private String resourceSearchPackage;
	private String resourceDefaultDomain;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.info("请求进入JsonApiServlet GET {0}", request.getRequestURI());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.info("请求进入JsonApiServlet POST {0}", request.getRequestURI());
	}

	@Override
	protected KatharsisInvokerBuilder createKatharsisInvokerBuilder() {
		return new KatharsisInvokerBuilder()
					.resourceSearchPackage(resourceSearchPackage)
					.resourceDefaultDomain(resourceDefaultDomain)
					.jsonServiceLocator(new SampleJsonServiceLocator());
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// 初始化jsonapi库配置
		//  katharsis.config.core.resource.package
		this.resourceSearchPackage = config.getInitParameter(KatharsisProperties.RESOURCE_SEARCH_PACKAGE);
		//  katharsis.config.core.resource.domain
		this.resourceDefaultDomain = config.getInitParameter(KatharsisProperties.RESOURCE_DEFAULT_DOMAIN);
	}
}
