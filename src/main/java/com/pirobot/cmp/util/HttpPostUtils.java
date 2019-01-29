/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

 
public class HttpPostUtils  {
   
	public static String httpPost(Map<String,String> params,String url) throws Exception 
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        for(String key:params.keySet()){
        	nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String data = EntityUtils.toString(response.getEntity());
        httpclient.close();
        response.close();
        return data;
	}
	
  
}
