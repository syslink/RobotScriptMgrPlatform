package com.pirobot.cmp.api.result;




public class XunfeiResult {

	public int rc ;
	public String service;
	public String source;
	public Object data;
	
	
	public boolean isSuccess(){
		return rc == 0;
	}
}
