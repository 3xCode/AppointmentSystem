package com.example.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {
	public String getTime() {
		long time = System.currentTimeMillis();// long now =
												// android.os.SystemClock.uptimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}

	public int getDateBetweenDate() {
		int re = -1;
		try {
			String d = "2016-09-04";
			Date date = new Date();
			long a = date.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long b = sdf.parse(d).getTime();
			int success = (int) ((a - b) / (1000 * 60 * 60 * 24)); // 1000毫秒*60分钟*60秒*24小时
																	// = 天
			System.out.println("距离" + d + "有" + success + "天");
			float w = 7f;
			float f = success / w;
			re = (int) Math.ceil(f);
		} catch (ParseException p) {
			p.printStackTrace();
		}

		return re;
	}

	public static String getBetween(int i) {
		String weekOfDate = getWeekOfDate();
		int j = Integer.parseInt(weekOfDate);

		int cha = i - j;
		if (cha < 0) {
			cha = cha + 7;
		}

		long time = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * cha;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}

	public static String getWeekOfDate() {
		Date d = new Date();
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static String getWeek(String s) {
		String res = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
		Date date = null;
		try {
			date = format.parse(s);// 将字符串转换为日期
			String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;
			res = weekDays[w];
		} catch (ParseException e) {
			System.out.println("输入的日期格式不合理！");
		}
		return res;
	}

	public static int compare_date(String DATE1) {
		long time = System.currentTimeMillis();// long now =
		// android.os.SystemClock.uptimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date(time);
		String t1 = format.format(d1);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(t1);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

}
