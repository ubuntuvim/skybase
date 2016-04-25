package com.skybase.util;

import org.apache.commons.lang3.StringUtils;

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
	
}
