package com.example.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	public static void MyToast(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}
}
