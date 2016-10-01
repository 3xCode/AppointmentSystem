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
	 * ���ڶ�Fragment���й���
	 */
	FragmentManager fragementManager;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Log.i("tag", "����UI");
				stu_msg.setText(app.getName());
			} else if (msg.what == 2) {
				Log.i("tag", "����UI");
				stu_msg.setText("���¼��");
			}

		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (CustomApplication) getApplication(); // ���CustomApplication����

		// ����̳�FragmentActivity������getSupportFragmentManager()�����ʹ��v4.app���Ѿ�û��ô����ʹ��app�е���
		fragementManager = getSupportFragmentManager();
		init();
		if (TextUtils.isEmpty(app.getName())) {
			ToastUtils.MyToast(getApplicationContext(), "��⵽δ��¼");
			showLoginDialog();
		} else {
			stu_msg.setText(app.getName());
		}

		// ��һ������ʱѡ�е�0��tab
		setTabSelection(0);// �������һ�����Ĭ��ֵ
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Socket socket = new Socket(app.serverAddress,
							Integer.parseInt(app.serverPort));
					socket.setSoTimeout(5000);
					app.setSocket(socket);
					System.out.println("socket��ʼ���ɹ�");
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					System.out.println(in.readLine());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
					ToastUtils.MyToast(getApplicationContext(), "����ʧ��");
				} catch (IOException e1) {
					e1.printStackTrace();
					ToastUtils.MyToast(getApplicationContext(), "����ʧ��");
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
				ToastUtils.MyToast(getApplicationContext(), "�˳��ɹ���");
				app.setName("");
				app.setPassword("");
				Message m = Message.obtain();
				m.what = 2;
				handler.sendMessage(m);
			} else {
				ToastUtils.MyToast(getApplicationContext(), "��δ��¼��");
			}
			break;

		default:
			break;

		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void setTabSelection(int index) {
		clearSelection();// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬

		// ����һ��Fragment����
		FragmentTransaction transaction = fragementManager.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
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
					System.out.println("��ȡ���ӳɹ�...");
					PrintWriter out = new PrintWriter(s.getOutputStream());
					System.out.println("����������ɹ�...");
					String msg = "action=searchroominfo" + "," + "roomindex="
							+ index;

					out.println(msg);
					out.flush();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(s.getInputStream()));
					System.out.println("��ȡ�������ɹ���");
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
									// ��Ϊ���������صĵ�һ�ڿε�numֵΪ0���ͻ������õ���1
									tv.setText("��������ѧϰ\n" + remaim);
								}
							});
						}

						// �ҵ��ؼ�set��ȥ

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
	 * ��������е�ѡ��״̬��
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
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
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
	 * ע��˵�
	 */
	private void showSignUpDialog() {
		final CustomDialogSignUp.Builder builder = new CustomDialogSignUp.Builder(
				this);
		builder.setTitle("���û�ע��");
		final int i;// ���շ������ӿڣ���Ϊ1��Ůλ0��
		builder.setPositiveButton("ע��", new DialogInterface.OnClickListener() {
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
							"����ֻ��Ϊ6-15λ��ĸ�����֣�");
				}

				if (cancel) {
					ToastUtils.MyToast(getApplicationContext(), "����������������룡");
					focusView.requestFocus();
				} else {

					new Thread(new Runnable() {
						public void run() {
							try {
								Socket s = app.getSocket();
								System.out.println("��ȡ���ӳɹ�...");
								PrintWriter out = new PrintWriter(s
										.getOutputStream());
								System.out.println("����������ɹ�...");

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
								System.out.println("��ȡ�������ɹ���");
								String result = in.readLine();
								System.out.println(result);
								Map<String, String> map = LoginResult
										.get(result);
								String re = map.get("status");

								Looper.prepare();
								if (re.equals(SUCCESS)) {

									ToastUtils.MyToast(getApplicationContext(),
											"ע��ɹ���");
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

		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}

	/*
	 * ��¼�˵�
	 */
	private void showLoginDialog() {

		final CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("��¼");
		builder.setPositiveButton("��¼", new DialogInterface.OnClickListener() {
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
					ToastUtils.MyToast(getApplicationContext(), "����������������룡");
					focusView.requestFocus();
				} else {

					new Thread(new Runnable() {
						public void run() {
							try {
								Socket s = app.getSocket();
								System.out.println("��ȡsocket�ɹ�");
								PrintWriter out = new PrintWriter(s
										.getOutputStream());
								System.out.println("����������ɹ���");
								String msg = "action=signin" + ","
										+ "username=" + id + ","
										+ "oldpassword=" + password;
								out.println(msg);
								out.flush();
								BufferedReader in = new BufferedReader(
										new InputStreamReader(s
												.getInputStream()));
								System.out.println("��ȡ�������ɹ���");
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
											"��¼�ɹ���");
									dialog.dismiss();
									//ˢ�µ�һ������
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

		builder.setNegativeButton("ע��",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showSignUpDialog();
					}
				});

		builder.create().show();
	}

	/**
	 * �޸�����˵�
	 */
	private void showChangePwdDialog() {
		final CustomDialogChangePwd.Builder builder = new CustomDialogChangePwd.Builder(
				this);
		builder.setTitle("�޸�����");
		builder.setPositiveButton("�޸�", new DialogInterface.OnClickListener() {
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
					ToastUtils.MyToast(getApplicationContext(), "����������������룡");
					focusView.requestFocus();
				} else {

					new Thread(new Runnable() {
						public void run() {
							try {
								Socket s = app.getSocket();
								System.out.println("��ȡsocket�ɹ�");
								PrintWriter out = new PrintWriter(s
										.getOutputStream());
								System.out.println("����������ɹ���");
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
								System.out.println("��ȡ�������ɹ���");
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
											"�޸ĳɹ��������µ�¼��");
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

		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	/**
	 * ��ѯ�ҵ�Ԥ��
	 */

	public void showMyOrderDialog() {
		new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				try {
					Socket s = app.getSocket();
					System.out.println("��ȡsocket�ɹ�");
					PrintWriter out = new PrintWriter(s.getOutputStream());
					System.out.println("����������ɹ���");
					String msg = "action=searchorderinfo" + "," + "username="
							+ app.getName() + "," + "oldpassword="
							+ app.getPassword();
					out.println(msg);
					out.flush();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(s.getInputStream()));
					System.out.println("��ȡ�������ɹ���");
					String result = in.readLine();
					List<OrderMessage> list = SearchOrderResult.get(result);
					String re = new SearchOrderResult().getStatus();
					if (re.equals(SUCCESS)) {

						for (int i = 0; i < list.size(); i++) {
							System.out.println(list.get(i));
						}
						show(list);

					} else {
						ToastUtils.MyToast(getApplicationContext(), "�鲻��Ԥ����Ϣ");
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

		// Courseû�и�ֵ������ᱨ��ָ��
		final Course[] c = CourseUtils.StringToCourses(message);
		final CustomDialogMyOrder.Builder builder = new CustomDialogMyOrder.Builder(
				this, data);

		if (message != "NULL") {
			builder.setMessage(c[0].toString());
		} else {
			builder.setMessage("�鲻��ԤԼ��Ϣ�������ԤԼ����");
		}

		builder.setTitle("�ҵ�Ԥ��");
		builder.setPositiveButton("ȡ��Ԥ��",
				new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, int which) {
						ToastUtils.MyToast(getApplicationContext(),
								"����������Ϣ��ȡ����Ӧ��ԤԼ��");
					}
				});

		builder.setNegativeButton("����",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
}