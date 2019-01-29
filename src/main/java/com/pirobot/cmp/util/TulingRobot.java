package com.pirobot.cmp.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.cmp.model.User;

public class TulingRobot {
	public static String getResponse(User self,String sentence)
	{
		
		
		String apiKey = ConfigManager.getInstance().getStringValue("TULING_API_KEY");
		String url = ConfigManager.getInstance().getStringValue("TULING_API_URL");
		String secret = ConfigManager.getInstance().getStringValue("TULING_API_SECRET");

		
		
		try {
			JSONObject dataJson = new JSONObject();
			dataJson.put("key", apiKey);
			dataJson.put("info", sentence);
			dataJson.put("userid", self.getUid().toString());
			
			//获取时间戳
			String timestamp = String.valueOf(System.currentTimeMillis());
			
			//生成密钥
			String keyParam = secret + timestamp + apiKey;
			String key = DigestUtils.md5Hex(keyParam);
			
			//加密
			String data = new Aes(key).encrypt(dataJson.toJSONString());
			dataJson.clear();

			
			//封装请求参数
			dataJson.put("key", apiKey);
			dataJson.put("timestamp", timestamp);
			dataJson.put("data", data);
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
	        
	        httpPost.setEntity(new StringEntity(dataJson.toJSONString(),"UTF-8"));
	        CloseableHttpResponse response = httpclient.execute(httpPost);
	        
	        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
        	dataJson = JSON.parseObject(result);
        	if(dataJson.getInteger("code") == 100000){
        		return dataJson.getString("text");
        	}
        	
        	
	        httpclient.close();
	        response.close();
				
		        
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	 
}
