package com.skybase.framework.action;

/**
 * 根据action配置的class属性值反射得到对应的类
 */
public class ActionManager {
	
	@SuppressWarnings("rawtypes")
	public static Action createAction(String className) {
		
		try {
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			//  判断当前线程是否运行了改action
			if (null == clazz) {
				clazz = Class.forName(className);
			}
			
			return (Action) clazz.newInstance();
			
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
