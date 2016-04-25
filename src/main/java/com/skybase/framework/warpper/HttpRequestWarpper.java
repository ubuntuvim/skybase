package com.skybase.framework.warpper;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestWarpper {

	public void requestWarpper(HttpServletRequest request) {
		// TODO 重新包装请求的内容
		/*
		 * 1. 验证请求的权限
		 * 2. 解析请求的参数
		 * 3. 根据请求查询数据库设置到Map中
		 * 4. 把查询到的数据以普通json格式返回到调用处
		 */
		
		Iterator<Entry<String, String[]>> iterator = request.getParameterMap().entrySet().iterator();
		StringBuffer param = new StringBuffer();
		int i = 0;
		while (iterator.hasNext()) {
			i++;
			Entry<String, String[]> entry = (Entry<String, String[]>) iterator.next();
			if (i == 1)
				param.append("?").append(entry.getKey()).append("=");
			else
				param.append("&").append(entry.getKey()).append("=");
			if (entry.getValue() instanceof String[]) {
				param.append(((String[]) entry.getValue())[0]);
			} else {
				param.append(entry.getValue());
			}
		}
		
		
	}

}
