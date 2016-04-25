package com.skybase.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

/**
 * 公共方法
 */
public class SkyBaseUtils {

	public static void checkConfigFiles(String configFiles, String msg) {
		if (StringUtils.isBlank(configFiles)) {
			try {
				throw new Exception("请在[com.skybase.framework.servlet.ActionServlet]中配置"+msg+"配置文件！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Map转Json格式
	 * @return
	 */
	public static String mapToJson(Map<String, Object> map) {
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
}
