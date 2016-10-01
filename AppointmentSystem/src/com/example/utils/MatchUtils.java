package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtils {

	public static boolean match(String s) {
		
		Pattern p = Pattern.compile("[0-9a-zA-Z]{6,15}"); 
		Matcher m = p.matcher(s); 
		return m.matches();
	}
}
