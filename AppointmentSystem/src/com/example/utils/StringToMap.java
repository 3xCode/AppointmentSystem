package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringToMap {
	
	public static List<Map<String, String>> StringToListImp(String s){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map ;
		String[] split = s.split(";");
		for (int i = 0; i < split.length; i++) {
			map = new HashMap<String, String>();
			String[] split2 = split[i].split(",");
			for (int j = 0; j < split2.length; j++) {
				String[] split3 = split2[j].split("=");
				map.put(split3[0], split3[1]);
			}
			list.add(map);
		}
		return list;
	}
}
