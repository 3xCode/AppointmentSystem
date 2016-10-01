package com.example.utils;

import android.content.Context;

public class MsgFromServer {
	public static void sendMessage(Context c,String message){
		if(message.equals("success")){
			ToastUtils.MyToast(c, "Ô¤¶©³É¹¦£¡");
		}else{
			ToastUtils.MyToast(c, message);
		}
	}
}
