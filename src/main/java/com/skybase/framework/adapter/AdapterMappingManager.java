package com.skybase.framework.adapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.skybase.framework.adapter.AdapterMapping;

/**
 * 读取adapters.xml并把每个子adapter元素转为Adapter对象
 * @author ubuntuvim
 */
public class AdapterMappingManager {
	// 参数一保存每个Adapter标签的name属性值，参数二保存转换后的Adapter标签对象
	Map<String, AdapterMapping> adapterMappings = new HashMap<String, AdapterMapping>();
	
	public AdapterMappingManager() {	}
	// 读取配置文件并解析
	public AdapterMappingManager(String[] configFiles) throws Exception {
		//  校验入参
		if (null == configFiles || configFiles.length <= 0) {
			throw new Exception("请指定Adapter的配置文件！");
		}
		// 遍历Adapter配置文件，并解析初始化到AdapterMappings中
		for (String cfgFile : configFiles) {
			readCfg(cfgFile);
		}
	}
	
	/**
	 * 根据Adapter类名获取Adapter配置
	 * @param AdapterName 名称
	 * @return AdapterMapping
	 * @throws Exception 
	 */
	public AdapterMapping getAdapterMapping(String adapterName) throws Exception {
		if (StringUtils.isBlank(adapterName)) {
			throw new Exception("入参AdapterName不允许为空！");
		}
		AdapterMapping am = this.adapterMappings.get(adapterName);
		if (null == am) {
			throw new Exception("没有Adapter["+adapterName+"]的配置！");
		}
		
		return am;
	}
	
	/**
	 * 返回所有的适配器配置
	 * @return
	 */
	public Map<String, AdapterMapping> getAllAdapterMapping() {
		return this.adapterMappings;
	}
	
	/**
	 * 读取Adapter配置，并设置到AdapterMapping map中
	 * @param cfgFile Adapter配置
	 */
	@SuppressWarnings("rawtypes")
	private void readCfg(String cfgFile) {

		try {
			//  解析Adapter配置
//				InputStream is = this.getClass().getResourceAsStream(cfgFile);
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(cfgFile);
			 // 创建saxReader对象  
	        SAXReader reader = new SAXReader();  
	        // 通过read方法读取一个文件 转换成Document对象  
	        Document doc = reader.read(is);   //new File("src/dom4j/sida.xml")
	        //获取根节点元素对象  
			Element adapters = doc.getRootElement();
			//  遍历每个Adapter标签
			Iterator it = adapters.elementIterator();
			while (it.hasNext()) {
				//  Adapter转换为AdapterMapping
				AdapterMapping am = new AdapterMapping();
				Element adapter = (Element) it.next();
				// 获取Adapter标签的属性name和class
				String adapterName = adapter.attributeValue("name");
				String adapterClassName = adapter.attributeValue("class");
				
				am.setAdapterName(adapterName);
				am.setAdapterClassName(adapterClassName);
				
				// 遍历Adapter下的property标签
				Iterator results = adapter.elementIterator();
				while (results.hasNext()) {
					Element result = (Element) results.next();
					// 获取property标签的属性值
					Map<String, String> prop = new HashMap<String, String>();
					prop.put("name", result.attributeValue("name"));
					prop.put("ref", result.attributeValue("ref"));
					prop.put("type", result.attributeValue("type"));
					am.setProp2List(prop);
				}
				
				adapterMappings.put(am.getAdapterName(), am);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
		
}
