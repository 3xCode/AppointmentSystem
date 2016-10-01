package com.example.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginResult {
	
	public static String SPLIT_S_M = ",";
	public static String SPLIT_S = "#";
	
	//status#error,msg#用户不存在
	public static Map<String, String> get(String s){
		Map<String, String> map = new HashMap<String, String>();
		String[] split2 = s.split(SPLIT_S_M);
		for (int i = 0; i < split2.length; i++) {
			String[] split = split2[i].split(SPLIT_S);
			for (int j = 0; j < split.length; j++) {
				map.put(split[0], split[1]);
			}
		}
		return map;
	}
}
