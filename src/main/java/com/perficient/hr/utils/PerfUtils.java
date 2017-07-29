package com.perficient.hr.utils;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class PerfUtils {

	private PerfUtils(){
		
	}
	
	public static String getUserId(HttpSession session){
		return session.getAttribute(PerfHrConstants.USER_ID).toString();
	}
	
	public static String capitalizeFully(String str){
		String[] valArray= str.split(" ");
        StringBuffer strBuffer= new StringBuffer();
        for (int i = 0; i < valArray.length; i++) {
        	strBuffer.append(Character.toUpperCase(valArray[i].charAt(0))).append(valArray[i].substring(1)).append(" ");
        }  
        return strBuffer.toString().trim();
	}
	
	public static boolean mandatory(String data){
		if(data != null && !"".equalsIgnoreCase(data)){
			return true;
		}
		return false;
	}

	public static String convertToJson(Object obj) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		return gson.toJson(obj);
	} 
	
}
