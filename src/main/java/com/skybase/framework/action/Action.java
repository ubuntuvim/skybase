package com.skybase.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

	public String INPUT = "input";
	public String ERROR = "error";
	public String SUCCESS = "success";
	
	/**
	 * 处理业务逻辑方法，每个Action类都需要重写此方法，并返回与配置文件对应的页面配置字符串
	 * 比如返回 'input'：
	 * <result name="input">index.jsp</result>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
