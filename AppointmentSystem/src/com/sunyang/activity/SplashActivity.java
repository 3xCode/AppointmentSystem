package com.sunyang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.fragmentmy.MainActivity;
import com.example.fragmentmytest.R;
import com.example.myapplication.CustomApplication;

public class SplashActivity extends FragmentActivity {

	private CustomApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sp);
		app = (CustomApplication) getApplication();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent i = new Intent();
				i.setClass(SplashActivity.this, MainActivity.class);
				startActivity(i);

			}
		}).start();

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.finish();
	}
}
