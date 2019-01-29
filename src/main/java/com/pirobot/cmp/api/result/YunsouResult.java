package com.pirobot.cmp.api.result;

import java.util.List;

public class YunsouResult {

	public String code;
	public String message;
	public	YunsouData data;
    public boolean isSuccess(){
		
		return "0".equals(code);
	}
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return data==null || data.result_num ==0;
	}
	
	public List<YunsouDoc> getDataList(){
		return data.result_list;
	}
	public int getCount(){
		return data.eresult_num;
	}
}
