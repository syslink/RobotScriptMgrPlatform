/**
 * probject:lvxin-server
 * @version 1.4.0
 * 
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.admin.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.pirobot.cmp.util.OssUtils;
import com.pirobot.cmp.util.StringUtil;


@Controller    
@RequestMapping("/console/file")
public class FileUploadController implements ServletContextAware{

	static final Logger logger = Logger.getLogger(FileUploadController.class);

	ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext context) {
		 this.servletContext = context;
	}
	
	@RequestMapping(value="/upload.action") 
	public void upload(HttpServletRequest request, MultipartFile file, HttpServletResponse response) throws IOException {

		String uid = "";
		String fileType = "";
		String fileName = StringUtil.getUUID();	
		
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组  
        if (cookies != null) {
            for(Cookie cookie : cookies){  
                if(cookie.getName().equals("uid"))
                {
              	    uid = cookie.getValue();
                }
                else if(cookie.getName().equals("fileType"))
                {
                	fileType = cookie.getValue();
                }
            }  
        }  
        	
        response.setContentType("text/html;charset=UTF-8");

		try{
			String ossFileName = fileName;
			if(!uid.isEmpty())
				ossFileName = uid + "/" + ossFileName;
			if(!fileType.isEmpty())
				ossFileName = fileType + "/" + ossFileName;
			OssUtils.upload(ossFileName, file.getInputStream());
	        IOUtils.closeQuietly(file.getInputStream());		
	        
			response.getWriter().print("<script>parent.onUploadCallback(200,'"+fileName+"');</script>");

		}catch(Exception e){
			e.printStackTrace();
			
			response.getWriter().print("<script>parent.onUploadCallback(500,'"+fileName+"');</script>");
		}
	}

}
