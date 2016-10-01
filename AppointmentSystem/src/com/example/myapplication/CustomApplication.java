package com.example.myapplication;

import java.net.Socket;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CustomApplication extends Application {
//	public static final String serverAddress = "http://192.168.1.119";
	public static final String serverAddress = "cc.hbu.cn";
//	public static final String serverAddress = "10.187.86.63";
//	public static final String serverPort = "8080/Person_proj/upload";
	public static final String serverPort = "3474";
	private static final String DEFAULT_NAME = "";
	private static final String DEFAULT_ID = "-1";
	private String name;
	private String id;
	private String room;
	private String myOrder="NULL";
	private boolean isLogin = false;
	private String password;
	private String jifangxinxi;
	private Socket socket;
	
	
	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public String getPassword() {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		return sp.getString("password", "");
	}


	public void setPassword(String password) {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("password", password);
		edit.commit();
	}


	public boolean isLogin() {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		return sp.getBoolean("islogin", false);
	}

	
	/**
	 * 0为 未登录
	 * 1为登录
	 */
	public void setLogin(boolean isLogin) {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean("islogin", isLogin);
		edit.commit();
	}

	private int version;
	
	public int getVersion() {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		return sp.getInt("version",0);
	}

	public void setVersion(int version) {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putInt("version",version);
		edit.commit();
	}


	public String getMyOrder() {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		return sp.getString("order","NULL");
	}

	public void setMyOrder(String myOrder) {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("order",myOrder);
		edit.commit();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		setName(DEFAULT_NAME); // 初始化全局变量
		setId(DEFAULT_ID);
	}

	public void setName(String name) {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("name", name);
		edit.commit();
	}

	public String getName() {
		SharedPreferences sp = getSharedPreferences("status", MODE_PRIVATE);
		return sp.getString("name", "none");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoom() {
		return room;
	}

}
