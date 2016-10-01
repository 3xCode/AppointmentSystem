package com.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class GetConnection {

	public static String openConnect(String url){
		String result = "";
		try {
			URL realUrl = new URL(url);
			Log.i("URL", url);
			URLConnection connection = realUrl.openConnection();
			connection.connect();
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection
							.getInputStream()));
			String line;
			
			while ((line = in.readLine()) != null) {
				result += line;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
