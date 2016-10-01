package com.example.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.dao.OrderMessage;
import com.example.dao.RoomMessage;

public class RoomMessageUtils {

	public static String SPLIT_S_M = ",";
	public static String SPLIT_S = "#";
	public static String SPLIT_M_M = ";";
	public static String SPLIT_M_M_M = "\\*";
	public static String SPLIT_M_E = "=";
	
	
	private static String result = "";
	
	public String getResult(){
		return result!=null?result:"error";
	}
	
	public static List<RoomMessage> get(String s){
		System.out.println("SearchOrderResult====1");
		List<RoomMessage> list = new ArrayList<RoomMessage>();
		RoomMessage rm;
		String[] split = s.split(SPLIT_S_M);
		String[] split5 = split[0].split(SPLIT_S);
		result = split5[1];
		System.out.println("SearchOrderResult====2");
		if (result.equals("success")) {
			
			String[] s1 = split[1].split(SPLIT_S);
			String[] split2 = s1[1].split(SPLIT_M_M);
			System.out.println("SearchOrderResult====3");
			System.out.println(split2.length);
			for (int i = 0; i < split2.length; i++) {
				System.out.println(split2[0]);
				rm = new RoomMessage();
				System.out.println("SearchOrderResult====4");
				// 一条信息
				String trim = split2[i].trim();
				
				String[] split3 = trim.split(SPLIT_M_M_M);
				System.out.println("SearchOrderResult====5");
				System.out.println(split3[0]);
				// split3 为 username=123 oder=123
				for (int j = 0; j < split3.length; j++) {
					String[] split4 = split3[j].split(SPLIT_M_E);
					
					if (split4[0].equals("appointmentdate")) {
						String replace = split4[1].replace("/", "-");
						String[] split6 = replace.split(" ");
						rm.setAppointmentdate(split6[0]);
					} else if (split4[0].equals("remainnum")) {
						rm.setRemaim(Integer.parseInt(split4[1]));
					} else if(split4[0].equals("classindex")){
						rm.setNum(Integer.parseInt(split4[1]));
					}
				}
				list.add(rm);
			}
			
		}else{
			//如果查不到的处理
			result = "error";
		}
		return list;
	}
}
