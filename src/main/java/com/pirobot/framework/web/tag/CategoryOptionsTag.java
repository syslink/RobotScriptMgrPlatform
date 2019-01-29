/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.framework.web.tag;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

 
public class CategoryOptionsTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;

	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {

		JspWriter out = pageContext.getOut();
		try {

			Properties prop = ScriptCategoryManager.getInstance().getProperties();
			for (Object key:prop.keySet()) {
				if(key.equals(this.key)){
					out.println("<option value=\""+key+"\"  selected=\"selected\">"+prop.getProperty(key.toString())+"</option>");
				}else
				{
					out.println("<option value=\""+key+"\">"+prop.getProperty(key.toString())+"</option>");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Tag.EVAL_PAGE;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
}
