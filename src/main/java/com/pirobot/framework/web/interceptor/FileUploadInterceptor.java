/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */
package com.pirobot.framework.web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 
public class FileUploadInterceptor   implements  HandlerInterceptor {


	private long maxSize;
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg2) throws Exception {
		 if(request!=null && ServletFileUpload.isMultipartContent(request)) {
	            ServletRequestContext ctx = new ServletRequestContext(request);
	            long requestSize = ctx.contentLength();
	            if (requestSize > maxSize) {
	            	response.getWriter().print("<script>parent.onUploadCallback(403,'');</script>");
	            	return false;
	            }
	        }
	        return true;

		}
		@Override
		public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)
				throws Exception {
			
		}
		@Override
		public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
				Object arg2, ModelAndView arg3) throws Exception {
			
		}
		public void setMaxSize(long maxSize) {
			this.maxSize = maxSize;
		}
		 
		
	 
}