package com.example.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.dao.OrderMessage;

public class SearchOrderResult {

	public static String SPLIT_S_M = ",";
	public static String SPLIT_S = "#";
	public static String SPLIT_M_M = ";";
	public static String SPLIT_M_M_M = "\\*";
	public static String SPLIT_M_E = "=";

	private static String status;

	public String getStatus() {
		return status;
	}

	public static List<OrderMessage> get(String s) {
		System.out.println("SearchOrderResult====1");
		List<OrderMessage> list = new ArrayList<OrderMessage>();
		OrderMessage om;
		String[] split = s.split(SPLIT_S_M);
		String[] split5 = split[0].split(SPLIT_S);
		status = split5[1];
		System.out.println("SearchOrderResult====2");
		if (status.equals("success")) {
			
			String[] s1 = split[1].split(SPLIT_S);
			String[] split2 = s1[1].split(SPLIT_M_M);
			System.out.println("SearchOrderResult====3");
			System.out.println(split2.length);
			for (int i = 0; i < split2.length; i++) {
				System.out.println(split2[0]);
				om = new OrderMessage();
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
						om.setAppointdate(split4[1]);
					} else if (split4[0].equals("roomindex")) {
						om.setClassindex(split4[1]);
					} else if (split4[0].equals("classindex")) {
						om.setNum(split4[1]);
					} else if(split4[0].equals("iscancel")){
						om.setIsCancel(split4[1]);
					}
				}
				list.add(om);
			}
			
			
			if (null != list && list.size() > 0) {
			    Iterator it = list.iterator();  
			    while(it.hasNext()){
			    	OrderMessage om1=  (OrderMessage)it.next(); 
			        if (om1.getIsCancel().equals("True")||DataUtils.compare_date(om1.getAppointdate())<0) {
			            it.remove(); //移除该对象
			        }
			    }
			}
			
			System.out.println("=================");
			
		}else{
			//如果查不到的处理
			status = "error";
		}
		
		
//		for (int i = 0; i < list.size(); i++) {
//			OrderMessage temp = list.get(i);
//			if(temp.getIsCancel().equals("True")){
//				list.remove(i);
//			}
//		}
		return list;
	}
}
