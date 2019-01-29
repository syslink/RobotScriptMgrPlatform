/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.framework.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

 
public class CategoryNameTag extends TagSupport {

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

			out.println(ScriptCategoryManager.getInstance().getValue(key));

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
