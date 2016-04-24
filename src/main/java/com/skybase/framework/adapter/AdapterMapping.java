package com.skybase.framework.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每个AdapterMapping对象对应着一个Adapter配置。
 * 
 * <adapter class="com.Test" name="test"> 
 * 		<property name="nae" ref="xxx" type="String"></property> 
 * </adapter>
 * 
 * Map<String, AdapterMapping> adapterMappings = new HashMap<String, AdapterMapping>();
 * @author ubuntuvim
 *
 */
public class AdapterMapping {

	private String adapterName;
	private String adapterClassName;
	// 保存每个adapter中的property
	private List<Map<String, String>> propList = new ArrayList<Map<String, String>>();
	// property数组，保存每个property的name ref type值
	private Map<String, String> propMap = new HashMap<String, String>();


	public String getAdapterName() {
		return adapterName;
	}

	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

	public String getAdapterClassName() {
		return adapterClassName;
	}

	public void setAdapterClassName(String adapterClassName) {
		this.adapterClassName = adapterClassName;
	}

	public Map<String, String> getPropViaList(int index) {
		return propList.get(index);
	}

	public void setProp2List(Map<String, String> e) {
		this.propList.add(e);
	}

	public String getPropMap(String propName) {
		return propMap.get(propName);
	}

	public void setPropMap(String key, String value) {
		this.propMap.put(key, value);
	}

}
