package com.example.adapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.dao.OrderMessage;
import com.example.dialog.CustomDialogMyOrder;
import com.example.fragmentmy.MainActivity;
import com.example.fragmentmytest.R;
import com.example.myapplication.CustomApplication;
import com.example.utils.LoginResult;
import com.example.utils.ToastUtils;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private List<OrderMessage> data;
	private LayoutInflater layoutInflater;
	private ViewHolder holder = null;
	CustomApplication app ;
	CustomDialogMyOrder dialog;
	public MyAdapter(Context context, List<OrderMessage> data,CustomDialogMyOrder dialog) {
		this.context = context;
		this.data = data;
		this.dialog = dialog;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.lv_item, null);
			holder.seatId = (TextView) convertView
					.findViewById(R.id.ordermessage);
			holder.cancel = (Button) convertView.findViewById(R.id.cancelorder);
			convertView.setTag(holder);
			app =(CustomApplication) ((Activity)context).getApplication();
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		// 获得集合中实体类对象
		final OrderMessage s = data.get(position);
	
		holder.seatId.setText(s.toString());
		holder.cancel.setOnClickListener(new lvButtonListener(position));

		return convertView;
	}

	class lvButtonListener implements View.OnClickListener {
		private int position;

		lvButtonListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cancelorder:
				OrderMessage orderMessage = data.get(position);
				final String appointdate = orderMessage.getAppointdate();
				final String classindex = orderMessage.getClassindex();
				final String num = orderMessage.getNum();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try{
							CustomApplication app = (CustomApplication) ((Activity)context).getApplication();
							Socket socket = app.getSocket();
							PrintWriter out = new PrintWriter(socket.getOutputStream());
							String msg = "action=cancel" + ",username=" + app.getName() + ",oldpassword=" + app.getPassword()+",appointmentdate="+appointdate+",roomindex="+classindex+",classindex="+num;
							Uri parse = Uri.parse(msg);
							System.out.println(parse.toString());
							out.println(msg);
							out.flush();
							
							BufferedReader in = new BufferedReader(
									new InputStreamReader(socket.getInputStream()));
							System.out.println("获取输入流成功！");
							String result = in.readLine();
							System.out.println("取消预约的返回结果："+result);
							
							Map<String, String> map = LoginResult
									.get(result);
							String re = map.get("status");
							
							Looper.prepare();
							if (re.equals("success")) {
								ToastUtils.MyToast(context,
										"取消成功！");
								dialog.dismiss();
								MainActivity m = (MainActivity)context;
								int room = m.getcurrentRoom();
								m.download(room);
							} else {
								app.setLogin(false);
								ToastUtils.MyToast(context,
										map.get("msg"));
							}
							Looper.loop();
						}catch(Exception e){
							e.printStackTrace();
						}
							
					}
				}).start();
				
				dialog.dismiss();
				break;
			default:
				break;
			}
		}
	}

}

class ViewHolder {
	TextView seatId;
	Button cancel;

}