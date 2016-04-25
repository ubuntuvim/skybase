package com.skybase.framework.container;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.skybase.framework.adapter.Adapter;
import com.skybase.framework.adapter.AdapterManager;
import com.skybase.framework.adapter.AdapterMapping;
import com.skybase.framework.adapter.AdapterMappingManager;

/**
 * 读取被实例化的adapter配置
 */
public class ClassPathXmlApplicationContext {
	
	private String[] cfgArr = null;
	
	public ClassPathXmlApplicationContext(String[] cfgArr) {
		this.cfgArr = cfgArr;
	}

	private Map<String, Object> container = new HashMap<>();

	/**
	 * 解析adapter配置文件，并把配置的类实例化到容器container中
	 * @return
	 */
	public Map<String, Object> initContainer() {
		try {
			AdapterMappingManager amm = new AdapterMappingManager(this.cfgArr);
			Map<String, AdapterMapping> adms = amm.getAllAdapterMapping();
//			AdapterManager adapterManager = new AdapterManager();
			for (Entry<String, AdapterMapping> entry : adms.entrySet()) {
//				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
//				String key = entry.getKey();  //key与className是同一个东西
				AdapterMapping am = entry.getValue();
				String className = am.getAdapterClassName();
				String adapterName = am.getAdapterName();
				Adapter adapter = AdapterManager.createAdapter(className);
				if (null != this.container.get(className)) {
					this.container.put(adapterName, adapter);
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("读取adapter配置文件出错，请确认配置的文件的路径！");
		}
		
		return this.container;
	}
	
	
}
