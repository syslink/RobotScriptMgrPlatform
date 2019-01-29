/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.framework.web.tag;
 
public class Functions {

	public static String chatAt(String source, int index) {
		if (source == null || index > source.length() - 1 || index < 0) {
			return "";
		}
		return String.valueOf(source.charAt(index));
	}
	
	public static String getTypeName(String key) {
		return ScriptCategoryManager.getInstance().getValue(key);
	}
}
