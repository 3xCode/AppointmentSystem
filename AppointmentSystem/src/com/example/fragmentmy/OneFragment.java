package com.example.fragmentmy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dao.Course;
import com.example.dialog.CustomDialogOrder;
import com.example.fragmentmytest.R;
import com.example.myapplication.CustomApplication;
import com.example.utils.DataUtils;
import com.example.utils.LoginResult;
import com.example.utils.ToastUtils;

public class OneFragment extends Fragment {

	// String room = "204";
	int room = 2;
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "msg";
	LinearLayout weekPanels[] = new LinearLayout[7];
	List courseData[] = new ArrayList[7];

	int itemHeight;
	int marTop, marLeft;
	CustomApplication app;
	Integer temp[] = new Integer[7];
	int count = 0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStatus) {
		View view = inflater.inflate(R.layout.onelayout, container, false);
		itemHeight = getResources().getDimensionPixelSize(
				R.dimen.weekItemHeight);
		marTop = getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
		marLeft = getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);
		app = (CustomApplication) getActivity().getApplication();
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		// 数据
		getData();

		for (int i = 0; i < weekPanels.length; i++) {
			weekPanels[i] = (LinearLayout) view.findViewById(R.id.weekPanel_1
					+ i);
			// initWeekPanel(weekPanels[i], courseData[i], roomMessage[i]);
			initWeekPanel(weekPanels[i], courseData[i]);
		}
	}

	public void getData() {
		List<Course> list1 = new ArrayList<Course>();

		Course c1 = new Course("网络自主学习", room, 3, 1, 3, 4);
		list1.add(c1);
		list1.add(new Course("网络自主学习", room, 4, 1, 4, 4));
		courseData[c1.getWeekday() - 1] = list1;

		List<Course> list2 = new ArrayList<Course>();
		list2.add(new Course("网络自主学习", room, 5, 1, 5, 5));
		courseData[4] = list2;

		List<Course> list3 = new ArrayList<Course>();
		list3.add(new Course("网络自主学习", room, 3, 1, 3, 7));
		list3.add(new Course("网络自主学习", room, 4, 1, 4, 7));
		courseData[6] = list3;
	}

	public void initWeekPanel(LinearLayout ll, List<Course> data) {

		if (ll == null || data == null || data.size() < 1)
			// if (ll == null || data == null || room == null || data.size() < 1
			// || room.size() < 1)
			return;
		Log.i("Msg", "初始化面板");
		Course pre = data.get(0);
		for (int i = 0; i < data.size(); i++) {
			final Course c = data.get(i);
			// RoomMessage roomMessage2 = room.get(i);
			// String remain = r.getRemaim() + "/" + r.getTotal();
			TextView tv = new TextView(getActivity());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, itemHeight
							* c.getStep() + marTop * (c.getStep() - 1));
			if (i > 0) {
				lp.setMargins(marLeft,
						(c.getStart() - (pre.getStart() + pre.getStep()))
								* (itemHeight + marTop) + marTop, 0, 0);
			} else {
				lp.setMargins(marLeft, (c.getStart() - 1)
						* (itemHeight + marTop) + marTop, 0, 0);
			}
			tv.setLayoutParams(lp);
			tv.setGravity(Gravity.TOP);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			tv.setTextSize(12);
			// 给view加一个id
			String s = room + "" + c.getWeekday() + "" + c.getClassindex();
			System.out.println("tvid:"+s);
			temp[count] = Integer.parseInt(s);
			tv.setId(temp[count]);

			tv.setTextColor(getResources().getColor(R.color.courseTextColor));
			tv.setText(c.getName() + "\n");
			tv.setBackgroundColor(getResources().getColor(R.color.classIndex));
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					showDialog(c);
				}
			});
			ll.addView(tv);
			pre = c;
			count++;
		}
	}

	public void showDialog(final Course c) {
		System.out.println("========" + temp[0]);
		String course = c.getName();
		final CustomDialogOrder.Builder builder = new CustomDialogOrder.Builder(
				getActivity());
		if (course != null) {
			builder.setMessage(course);
		}
		builder.setTitle("预约上机");
		builder.setPositiveButton("预约", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, int which) {
					new Thread(new Runnable() {
						public void run() {
							try {
								//处理一下getWeekday
								//获得当前星期数
								String between = DataUtils.getBetween(c.getWeekday());
								
								CustomApplication app = (CustomApplication) getActivity()
										.getApplication();
								Socket s = app.getSocket();
								PrintWriter out = new PrintWriter(s
										.getOutputStream());
								String msg = "action=order" + "," + "username="
										+ app.getName() + ",oldpassword="
										+ app.getPassword()
										+ ",appointmentdate=" + between
										+ ",classindex=" + (c.getClassindex()-1)
										+ ",roomindex=" + c.getRoom();

								out.println(msg);
								out.flush();
								System.out.println(msg);
								BufferedReader in = new BufferedReader(
										new InputStreamReader(s
												.getInputStream()));
								System.out.println("获取输入流成功！");
								String result = in.readLine();
								System.out.println(result);
								Map<String, String> map = LoginResult
										.get(result);
								String re = map.get("status");
								Looper.prepare();
								if (re.equals(SUCCESS)) {
									ToastUtils.MyToast(getActivity(),
											"预定成功！");
									MainActivity m = (MainActivity)getActivity();
									m.download(room);
								} else {
									app.setLogin(false);
									ToastUtils.MyToast(getActivity(),
											map.get("msg"));
									
								}
								Looper.loop();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
					dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});

		builder.create().show();
	}
}
