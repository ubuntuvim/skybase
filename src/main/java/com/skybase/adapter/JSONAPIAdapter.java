package com.skybase.adapter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skybase.framework.adapter.Adapter;

/**
 * 数据适配器，数据格式遵照jsonapi（http://jsonapi.org/）
 */
public class JSONAPIAdapter implements Adapter {

	@Override
	public Map<String, Object> adapter(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//TODO  转换请求和相应中的json数据格式
		
		return null;
	}
	
}
