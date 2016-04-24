package com.skybase.framework.adapter;

/**
 * 根据配置文件adapters.xml中adapter标签的name和class属性反射得到实例
 * @author ubuntuvim
 */
public class AdapterManager {

	@SuppressWarnings("rawtypes")
	public static Adapter createAdapter(String className) {
		
		try {
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			//  判断当前线程是否运行了改action
			if (null == clazz) {
				clazz = Class.forName(className);
			}
			
			return (Adapter) clazz.newInstance();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
