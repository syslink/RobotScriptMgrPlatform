/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class StringUtil {

	public static boolean isEmpty(Object obj) {
		if (null == obj)
			return true;
		if ("".equals(obj.toString().trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(Object obj) {

		return !isEmpty(obj);
	}

	public static String getSequenceId() {
		String mark = String.valueOf(System.currentTimeMillis());
		return mark;
	}

	public static String getCurrentlyDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}

	public static String transformDateTime(long t) {
		Date date = new Date(t);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String getCurrentlyDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(new Date());
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	
	public static String convertNumInStr(String sentence)
	{
		if(sentence==null){
			return null;
		}
		List<String> numberList = getNumber(sentence);
		for(String number : numberList)
		{
			String convertStr = convertNumToStr(number);
			sentence = sentence.replace(number, convertStr);
		}
		return sentence;
	}
	public static List<String> getNumber(String sentence)
	{
		List<String> numberList = new ArrayList<String>();
		Pattern p = Pattern.compile("[0-9\\.]+");
		Matcher m = p.matcher(sentence);

		while(m.find()){
			numberList.add(m.group());  
		}
		return numberList;
	}
	public static String convertNumToStr(String number)
	{
		String[] units = new String[] {"十", "百", "千", "万", "十", "百", "千", "亿"};  
        String[] numeric = new String[] {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};  
          
        String result = "";  
          
        // 遍历一行中所有数字  
        for (int k = -1; number.length() > 0; k++)  
        {  
            // 解析最后一位  
            int j = Integer.parseInt(number.substring(number.length() - 1, number.length()));  
            String rnumber = numeric[j];  
              
            // 数值不是0且不是个位 或者是万位或者是亿位 则去取单位  
            if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7)  
            {  
                rnumber += units[k % 8];  
            }  
              
            // 拼在之前的前面  
            result = rnumber + result;  
              
            // 去除最后一位  
            number = number.substring(0, number.length() - 1);  
        }  
          
        // 去除后面连续的零零..  
        while (result.endsWith(numeric[0]))  
        {  
        	result = result.substring(0, result.lastIndexOf(numeric[0]));  
        }  
          
        // 将零零替换成零  
        while (result.indexOf(numeric[0] + numeric[0]) != -1)  
        {  
        	result = result.replaceAll(numeric[0] + numeric[0], numeric[0]);  
        }  
          
        // 将 零+某个单位 这样的窜替换成 该单位 去掉单位前面的零  
        for (int m = 1; m < units.length; m++)  
        {  
        	result = result.replaceAll(numeric[0] + units[m], units[m]);  
        }  
        return result;
	}
	
}
