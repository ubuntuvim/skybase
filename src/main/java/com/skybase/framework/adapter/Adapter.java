package com.skybase.framework.adapter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Adapter {
	
	/**
	 * 每个框架内置的和自定义的适配器都要实现这个接口
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> adapter(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
