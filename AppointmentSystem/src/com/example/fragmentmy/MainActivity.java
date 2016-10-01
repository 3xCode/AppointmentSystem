package com.example.fragmentmy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.dao.Course;
import com.example.dao.OrderMessage;
import com.example.dao.RoomMessage;
import com.example.dialog.CustomDialog;
import com.example.dialog.CustomDialogChangePwd;
import com.example.dialog.CustomDialogMyOrder;
import com.example.dialog.CustomDialogSignUp;
import com.example.fragmentmytest.R;
import com.example.myapplication.CustomApplication;
import com.example.utils.CourseUtils;
import com.example.utils.LoginResult;
import com.example.utils.MatchUtils;
import com.example.utils.RoomMessageUtils;
import com.example.utils.SearchOrderResult;
import com.example.utils.ToastUtils;

public class MainActivity extends FragmentActivity {

	OneFragment onefragment;
	TwoFragment twofragment;
	ThreeFragment threefragment;
	FourFragment fourfragment;
	Button btn1, btn2, btn3, btn4;
	OnClickListener clicklistener;
	TextView stu_msg;
	private CustomApplication app;
	String checkRoomInfo;
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "msg";
	int currentRoom = -1;

	public int getcurrentRoom() {
		return currentRoom;
	}

	/**
	 * 用于对Fragment进行管理
	 */
	FragmentManager fragementManager;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Log.i("tag", "更新UI");
				stu_msg.setText(app.getName());
			} else if (msg.what == 2) {
				Log.i("tag", "更新UI");
				stu_msg.setText("请登录！");
			}

		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (CustomApplication) getApplication(); // 获得CustomApplication对象

		// 必须继承FragmentActivity才能用getSupportFragmentManager()；最好使用v4.app，已经没怎么有人使用app中的了
		fragementManager = getSupportFragmentManager();
		init();
		if (TextUtils.isEmpty(app.getName())) {
			ToastUtils.MyToast(getApplicationContext(), "检测到未登录");
			showLoginDialog();
		} else {
			stu_msg.setText(app.getName());
		}

		// 第一次启动时选中第0个tab
		setTabSelection(0);// 不能左右滑动的默认值
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Socket socket = new Socket(app.serverAddress,
							Integer.parseInt(app.serverPort));
					socket.setSoTimeout(5000);
					app.setSocket(socket);
					System.out.println("socket初始化成功");
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					System.out.println(in.readLine());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
					ToastUtils.MyToast(getApplicationContext(), "连接失败");
				} catch (IOException e1) {
					e1.printStackTrace();
					ToastUtils.MyToast(getApplicationContext(), "连接失败");
				}
			}
		}).start();
	}

	public void init() {
		stu_msg = (TextView) findViewById(R.id.stu_msg);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);

		clicklistener = new OnClickListener() {
			public void onClick(View arg0) {
				int id = arg0.getId();
				switch (id) {
				case R.id.btn1:
					setTabSelection(0);
					break;
				case R.id.btn2:
					setTabSelection(1);
					break;
				case R.id.btn3:
					setTabSelection(2);
					break;
				case R.id.btn4:
					setTabSelection(3);
					break;
				default:
					break;
				}
			}
		};
		btn1.setOnClickListener(clicklistener);
		btn2.setOnClickListener(clicklistener);
		btn3.setOnClickListener(clicklistener);
		btn4.setOnClickListener(clicklistener);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.login:
			showLoginDialog();

			break;
		case R.id.signup:
			showSignUpDialog();
			break;
		case R.id.changepwd:
			showChangePwdDialog();

			break;
		case R.id.myorder:
			showMyOrderDialog();
			break;

		case R.id.logout:
			if (app.isLogin()) {
				app.setLogin(false);
				ToastUtils.MyToast(getApplicationContext(), "退出成功！");
				app.setName("");
				app.setPassword("");
				Message m = Message.obtain();
				m.what = 2;
				handler.sendMessage(m);
			} else {
				ToastUtils.MyToast(getApplicationContext(), "还未登录！");
			}
			break;

		default:
			break;

		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void setTabSelection(int index) {
		clearSelection();// 每次选中之前先清楚掉上次的选中状态

		// 开启一个Fragment事务
		FragmentTransaction transaction = fragementManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragements(transaction);

		switch (index) {

		case 0:
			btn1.setBackgroundColor(Color.parseColor("#CFEFEF"));
			btn1.setTextColor(Color.parseColor("#FFFFFF"));
			app.setRoom(btn1.getText().toString());
			String url = checkRoomInfo + index;
			currentRoom = 2;
			if (onefragment == null) {
				onefragment = new OneFragment();
				transaction.add(R.id.framelayout, onefragment);
				download(2);
			} else {
				transaction.show(onefragment);
				download(2);
			}

			break;
		case 1:
			btn2.setBackgroundColor(Color.parseColor("#CFEFEF"));
			btn2.setTextColor(Color.parseColor("#FFFFFF"));
			app.setRoom(btn2.getText().toString());
			String url2 = checkRoomInfo + index;
			currentRoom = 3;
			if (twofragment == null) {
				twofragment = new TwoFragment();
				transaction.add(R.id.framelayout, twofragment);
				download(3);
			} else {
				transaction.show(twofragment);
				download(3);
			}
			break;
		case 2:
			btn3.setBackgroundColor(Color.parseColor("#CFEFEF"));
			btn3.setTextColor(Color.parseColor("#FFFFFF"));
			app.setRoom(btn3.getText().toString());
			String url3 = checkRoomInfo + index;
			currentRoom = 0;
			if (threefragment == null) {
				threefragment = new ThreeFragment();
				transaction.add(R.id.framelayout, threefragment);
				download(0);
			} else {
				transaction.show(threefragment);
				download(0);
			}
			break;
		case 3:
			btn4.setBackgroundColor(Color.parseColor("#CFEFEF"));
			btn4.setTextColor(Color.parseColor("#FFFFFF"));
			app.setRoom(btn4.getText().toString());
			String url4 = checkRoomInfo + index;
			currentRoom = 1;
			if (fourfragment == null) {
				fourfragment = new FourFragment();
				transaction.add(R.id.framelayout, fourfragment);
				download(1);
			} else {
				transaction.show(fourfragment);
				download(1);
			}
			break;

		default:
			break;

		}
		transaction.commit();
	}

	interface OnFlushListener {
		void download(final int index);
	}

	public void download(final int index) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Socket s = app.getSocket();
					System.out.println("获取连接成功...");
					PrintWriter out = new PrintWriter(s.getOutputStream());
					System.out.println("建立输出流成功...");
					String msg = "action=searchroominfo" + "," + "roomindex="
							+ index;

					out.println(msg);
					out.flush();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(s.getInputStream()));
					System.out.println("获取输入流成功！");
					String result = in.readLine();
					System.out.println(result);

					final List<RoomMessage> list = RoomMessageUtils.get(result);
					Thread.sleep(500);
					Looper.prepare();
					if (new RoomMessageUtils().getResult().equals(SUCCESS)) {
						System.out.println(list);
						for (int i = 0; i < list.size(); i++) {
							int num = list.get(i).getNum() + 1;
							String viewId = index + ""
									+ list.get(i).getAppointmentdate() + ""
									+ num;
							System.out.println("viewId===" + viewId);
							int viewIDD = Integer.parseInt(viewId);
							final TextView tv = (TextView) findViewById(viewIDD);
							final int remaim = list.get(i).getRemaim();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									// 因为服务器返回的第一节课的num值为0，客户端设置的是1
									tv.setText("网络自主学习\n" + remaim);
								}
							});
						}

						// 找到控件set上去

					} else {

					}
					Looper.loop();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		btn1.setBackgroundColor(Color.parseColor("#EFEFEF"));
		btn1.setTextColor(Color.parseColor("#234567"));
		btn2.setBackgroundColor(Color.parseColor("#EFEFEF"));
		btn2.setTextColor(Color.parseColor("#234567"));
		btn3.setBackgroundColor(Color.parseColor("#EFEFEF"));
		btn3.setTextColor(Color.parseColor("#234567"));
		btn4.setBackgroundColor(Color.parseColor("#EFEFEF"));
		btn4.setTextColor(Color.parseColor("#234567"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragements(FragmentTransaction transaction) {
		if (onefragment != null) {
			transaction.hide(onefragment);
		}
		if (twofragment != null) {
			transaction.hide(twofragment);
		}
		if (threefragment != null) {
			transaction.hide(threefragment);
		}
		if (fourfragment != null) {
			transaction.hide(fourfragment);
		}
	}

	// _______________________________________________________
	/*
	 * 注册菜单
	 */
	private void showSignUpDialog() {
		final CustomDialogSignUp.Builder builder = new CustomDialogSignUp.Builder(
				this);
		builder.setTitle("新用户注册");
		final int i;// 按照服务器接口，男为1，女位0；
		builder.setPositiveButton("注册", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, int which) {

				boolean cancel = false;
				View focusView = null;

				final String id = builder.getUserId().trim();
				final String password = builder.getPassword().trim();
				final String passwordConfirm = builder.getPasswordConfirm()
						.trim();
				final String username = builder.getUsername().trim();
				final int gender = builder.getGender();
				final String stuClass = builder.getStuClass().trim();

				if (gender == -1) {
					cancel = true;
					focusView = builder.getGenderEditText();
				}

				if (TextUtils.isEmpty(stuClass)) {
					cancel = true;
					focusView = builder.getStuClassEditText();
				}

				if (TextUtils.isEmpty(password)) {
					cancel = true;
					focusView = builder.getStuPwdEditText();
				}

				if (TextUtils.isEmpty(passwordConfirm)) {
					cancel = true;
					focusView = builder.getStuPwdConfirmEditText();
				}

				if (!password.equals(passwordConfirm)) {
					cancel = true;
					focusView = builder.getStuPwdEditText();
				}

				if (TextUtils.isEmpty(id)) {
					cancel = true;
					focusView = builder.getStuIdEditText();
				}

				if (TextUtils.isEmpty(username)) {
					cancel = true;
					focusView = builder.getStuNameEditText();
				}

				if (!MatchUtils.match(password)
						|| !MatchUtils.match(passwordConfirm)) {
					cancel = true;
					focusView = builder.getStuPwdEditText();
					ToastUtils.MyToast(getApplicationContext(),
							"密码只能为6-15位字母或数字！");
				}

				if (cancel) {
					ToastUtils.MyToast(getApplicationContext(), "输入有误，请从新输入！");
					focusView.requestFocus();
				} else {

					new Thread(new Runnable() {
						public void run() {
							try {
								Socket s = app.getSocket();
								System.out.println("获取连接成功...");
								PrintWriter out = new PrintWriter(s
										.getOutputStream());
								System.out.println("建立输出流成功...");

								String msg = "action=signup" + ","
										+ "username=" + id + ","
										+ "oldpassword=" + password
										+ ",gender=" + gender + ",grade="
										+ stuClass + ",personname=" + username;

								out.println(msg);
								out.flush();
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

									ToastUtils.MyToast(getApplicationContext(),
											"注册成功！");
									dialog.dismiss();

								} else {
									app.setLogin(false);
									ToastUtils.MyToast(getApplicationContext(),
											map.get("msg"));
								}
								Looper.loop();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();

				}
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

	/*
	 * 登录菜单
	 */
	private void showLoginDialog() {

		final CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("登录");
		builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, int which) {

				boolean cancel = false;
				View focusView = null;

				final String id = builder.getUserId().trim();
				final String password = builder.getPassword().trim();

				if (TextUtils.isEmpty(password)) {
					cancel = true;
					focusView = builder.getStuPwdEditText();
				}

				if (TextUtils.isEmpty(id)) {
					cancel = true;
					focusView = builder.getStuIdEditText();
				}

				if (cancel) {
					ToastUtils.MyToast(getApplicationContext(), "输入有误，请从新输入！");
					focusView.requestFocus();
				} else {

					new Thread(new Runnable() {
						public void run() {
							try {
								Socket s = app.getSocket();
								System.out.println("获取socket成功");
								PrintWriter out = new PrintWriter(s
										.getOutputStream());
								System.out.println("建立输出流成功！");
								String msg = "action=signin" + ","
										+ "username=" + id + ","
										+ "oldpassword=" + password;
								out.println(msg);
								out.flush();
								BufferedReader in = new BufferedReader(
										new InputStreamReader(s
												.getInputStream()));
								System.out.println("获取输入流成功！");
								String result = in.readLine();
								Map<String, String> map = LoginResult
										.get(result);
								String re = map.get("status");
								Looper.prepare();
								if (re.equals(SUCCESS)) {
									app.setName(id);
									app.setLogin(true);
									app.setPassword(password);
									Message m = Message.obtain();
									m.what = 1;
									handler.sendMessage(m);

									ToastUtils.MyToast(getApplicationContext(),
											"登录成功！");
									dialog.dismiss();
									//刷新第一个界面
									download(2);
									

								} else {
									app.setLogin(false);
									ToastUtils.MyToast(getApplicationContext(),
											map.get("msg"));
								}
								Looper.loop();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();

				}
			}
		});

		builder.setNegativeButton("注册",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showSignUpDialog();
					}
				});

		builder.create().show();
	}

	/**
	 * 修改密码菜单
	 */
	private void showChangePwdDialog() {
		final CustomDialogChangePwd.Builder builder = new CustomDialogChangePwd.Builder(
				this);
		builder.setTitle("修改密码");
		builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, int which) {
				dialog.dismiss();

				boolean cancel = false;
				View focusView = null;

				final String id = builder.getUserId().trim();
				final String password = builder.getPassword().trim();
				final String passwordNew = builder.getPwdNew().trim();

				if (TextUtils.isEmpty(password)) {
					cancel = true;
					focusView = builder.getStuPwdEditText();
				}

				if (TextUtils.isEmpty(id)) {
					cancel = true;
					focusView = builder.getStuIdEditText();
				}

				if (TextUtils.isEmpty(passwordNew)) {
					cancel = true;
					focusView = builder.getStuPwdNewEditText();
				}

				if (cancel) {
					ToastUtils.MyToast(getApplicationContext(), "输入有误，请从新输入！");
					focusView.requestFocus();
				} else {

					new Thread(new Runnable() {
						public void run() {
							try {
								Socket s = app.getSocket();
								System.out.println("获取socket成功");
								PrintWriter out = new PrintWriter(s
										.getOutputStream());
								System.out.println("建立输出流成功！");
								String msg = "action=changepassword" + ","
										+ "username=" + id + ","
										+ "oldpassword=" + password
										+ ",newpassword=" + passwordNew;
								System.out.println(msg);
								out.println(msg);
								out.flush();
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
									app.setLogin(false);
									app.setName("");
									app.setPassword("");
									Message m = Message.obtain();
									m.what = 1;
									handler.sendMessage(m);
									ToastUtils.MyToast(getApplicationContext(),
											"修改成功，请重新登录！");
									dialog.dismiss();

								} else {
									app.setLogin(false);
									ToastUtils.MyToast(getApplicationContext(),
											map.get("msg"));
								}
								Looper.loop();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
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

	/**
	 * 查询我的预定
	 */

	public void showMyOrderDialog() {
		new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				try {
					Socket s = app.getSocket();
					System.out.println("获取socket成功");
					PrintWriter out = new PrintWriter(s.getOutputStream());
					System.out.println("建立输出流成功！");
					String msg = "action=searchorderinfo" + "," + "username="
							+ app.getName() + "," + "oldpassword="
							+ app.getPassword();
					out.println(msg);
					out.flush();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(s.getInputStream()));
					System.out.println("获取输入流成功！");
					String result = in.readLine();
					List<OrderMessage> list = SearchOrderResult.get(result);
					String re = new SearchOrderResult().getStatus();
					if (re.equals(SUCCESS)) {

						for (int i = 0; i < list.size(); i++) {
							System.out.println(list.get(i));
						}
						show(list);

					} else {
						ToastUtils.MyToast(getApplicationContext(), "查不到预订信息");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Looper.loop();
			}
		}).start();
	}

	public void show(List<OrderMessage> data) {
		String message = app.getMyOrder();

		// Course没有赋值，这里会报空指针
		final Course[] c = CourseUtils.StringToCourses(message);
		final CustomDialogMyOrder.Builder builder = new CustomDialogMyOrder.Builder(
				this, data);

		if (message != "NULL") {
			builder.setMessage(c[0].toString());
		} else {
			builder.setMessage("查不到预约信息，请从新预约。。");
		}

		builder.setTitle("我的预定");
		builder.setPositiveButton("取消预定",
				new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, int which) {
						ToastUtils.MyToast(getApplicationContext(),
								"点击上面的信息来取消对应的预约。");
					}
				});

		builder.setNegativeButton("返回",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
}