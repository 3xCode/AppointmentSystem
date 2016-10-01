package com.example.utils;


public class HttpConnectionxUtils {

	String url;
	String result;
	public static final String ERROR= "ERROR";
	
	public static HttpConnectionxUtils builder(String url){
		
		return new HttpConnectionxUtils(url);
	}
	
	public HttpConnectionxUtils(String url) {
		this.url = url;
	}

}
