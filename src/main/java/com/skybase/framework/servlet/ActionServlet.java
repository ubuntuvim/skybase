package com.skybase.framework.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.skybase.framework.action.Action;
import com.skybase.framework.action.ActionManager;
import com.skybase.framework.action.ActionMapping;
import com.skybase.framework.action.ActionMappingManager;
import com.skybase.util.LogUtil;
import com.skybase.util.SkyBaseUtils;

/**
 * action的总控制器，所有以".action"结尾的请求都会被这个servlet拦截，并进入到框架中处理
 */
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ActionMappingManager amm = null;
       
    public ActionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * 截取请求，如果请求是以".action"结尾则进入到框架中处理
	 * 否则不处理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			// amm 在init方法中初始化
			ActionMapping am = amm.getActionMapping(getActionName(request));
			//  获取action实例
			Action action = ActionManager.createAction(am.getActionClassName());
			//  执行实现类的业务逻辑
			String result = action.execute(request, response);
			if (StringUtils.isBlank(result)) {
				response.sendError(404, "未配置Action对应的input元素。");
				return;
			}
			
			Map<String, String> resultMap = am.getResult(result);
			//  map的key暂时写死
			if (Boolean.parseBoolean(resultMap.get("REDIRECT"))) {
				response.sendRedirect(resultMap.get("RESULT_CONTENT"));
			} else {
				request.getRequestDispatcher(resultMap.get("RESULT_CONTENT")).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析URL获取action的名称
	 * @param request 请求
	 * @return
	 */
	private String getActionName(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String actionPath = request.getRequestURI().substring(contextPath.length());
		
		return actionPath.substring(1, actionPath.lastIndexOf(".")).trim();
	}

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//  读取action的配置信息
		String configFiles = config.getInitParameter("actionConfigFiles");
		SkyBaseUtils.checkConfigFiles(configFiles, "action");

		try {
			//  先去除分割配置文件间的空格（如果有）
			this.amm = new ActionMappingManager(configFiles.replaceAll("\\s*", "").split(","));
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtil.info("####### Action配置加载完成  #########");
	}
	
}
