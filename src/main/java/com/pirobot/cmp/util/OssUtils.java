package com.pirobot.cmp.util;

import java.io.File;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;

public class OssUtils {
	final static String OSS_ACCESS_KEY = "AD5AgZdRPH411hf2SjLVo3lPjoBrIS";
	final static String OSS_ACCESS_ID = "mX5GgMHT7fJ3EGCl";
	final static String OSS_BUCKET_NAME = "doulaig";
	final static String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com"; 
	 
	public static void upload(String key,File file){
		OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
		client.putObject(OSS_BUCKET_NAME, key, file);
		client.shutdown();
	}
 
	public static void upload(String key,InputStream stream){
		OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
		client.putObject(OSS_BUCKET_NAME, key, stream);
		client.shutdown();
	}
 
	
    public static void delete(String key){
    	OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
		client.deleteObject(OSS_BUCKET_NAME, key);
		client.shutdown();
	}

    public static boolean download(String path, String key){
    	boolean  success = false;
    	try{
    		OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
        	if(client.doesObjectExist(OSS_BUCKET_NAME, key)){
        		File target = new File(path,key);
            	client.getObject(new GetObjectRequest(OSS_BUCKET_NAME, key),target);        		
        		success = true;
        	}
        	
    		client.shutdown();	

    	}catch(Exception e){
    	}
    	
        return success;
	}
    public static void main(String[] args)
    {
    	OssUtils.download("F:/", "12345/test1222345");
    }
}