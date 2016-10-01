package com.example.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fragmentmytest.R;

public class CustomDialogSignUp extends Dialog {

	public CustomDialogSignUp(Context context) {
		super(context);
	}

	public CustomDialogSignUp(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;
		private CheckBox mycheckBox;
		private EditText stuId, stuName, stuPwd, stuPwdConfirm, gender,
				stuClass;
		private TextView serviceContent;

		public String getStuClass() {
			if (stuClass != null) {
				return stuClass.getText().toString();
			} else {
				return "stuClass is null";
			}
		}

		public EditText getStuClassEditText() {
			if (stuClass != null) {
				return stuClass;
			} else {
				return null;
			}
		}

		public int getGender() {
			return gender != null ? gender.getText().toString().trim()
					.equals("男") ? 1 : 0 : -1;
		}

		public EditText getGenderEditText() {
			if (gender != null) {
				return gender;
			} else {
				return null;
			}
		}

		public String getUsername() {
			if (stuName != null) {
				return stuName.getText().toString();
			} else {
				return "stuName is null!";
			}
		}

		public String getUserId() {
			if (stuId != null) {
				return stuId.getText().toString();
			} else {
				return "stuId is null!";
			}

		}

		public String getPassword() {
			if (stuPwd != null) {
				return stuPwd.getText().toString();
			} else {
				return "stuPwd is null!";
			}
		}

		public String getPasswordConfirm() {
			if (stuPwdConfirm != null) {
				return stuPwdConfirm.getText().toString();
			} else {
				return "stuPwd is null!";
			}
		}

		public EditText getStuIdEditText() {
			if (stuId != null) {
				return stuId;
			} else {
				return null;
			}
		}

		public EditText getStuNameEditText() {
			if (stuName != null) {
				return stuName;
			} else {
				return null;
			}
		}

		public EditText getStuPwdEditText() {
			if (stuPwd != null) {
				return stuPwd;
			} else {
				return null;
			}
		}

		public EditText getStuPwdConfirmEditText() {
			if (stuPwdConfirm != null) {
				return stuPwdConfirm;
			} else {
				return null;
			}
		}

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public CustomDialogSignUp create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialogSignUp dialog = new CustomDialogSignUp(context,
					R.style.Dialog);
			final View layout = inflater.inflate(
					R.layout.dialog_normal_layout_signup, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);
			serviceContent = (TextView) layout
					.findViewById(R.id.serviceContent);
			serviceContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
			serviceContent.getPaint().setAntiAlias(true);// 抗锯齿
			serviceContent.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// 点击服务条款，弹出对话框
					ScrollView sc = new ScrollView(context);
					// 背景色
					sc.setBackgroundColor(Color.WHITE);
					TextView tv = new TextView(context);
					tv.setTextSize(20);
					// 标题
//					tv.setText(Html.fromHtml(context.getResources().getString(
//							R.string.registercontent)));
					tv.setText(R.string.registercontent);
					// tv.setTextColor(getResources().getDrawable(R.color.blue));
					sc.addView(tv);
					// 内容
					new AlertDialog.Builder(context)
							.setTitle(R.string.resgister_ad_clause_title)
							.setView(sc).create().show();
				}
			});
			stuId = (EditText) layout.findViewById(R.id.stuId);
			stuPwd = (EditText) layout.findViewById(R.id.stuPwd);
			stuName = (EditText) layout.findViewById(R.id.stuName);
			stuPwdConfirm = (EditText) layout.findViewById(R.id.stuPwdConfirm);
			gender = (EditText) layout.findViewById(R.id.gender);
			stuClass = (EditText) layout.findViewById(R.id.stuclass);

			mycheckBox = (CheckBox) layout.findViewById(R.id.checkbox);

			final Button b = (Button) layout.findViewById(R.id.positiveButton);
			b.setEnabled(false);
			b.setBackgroundResource(R.drawable.btn_cancel);
			mycheckBox.setChecked(false);
			mycheckBox.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (mycheckBox.isChecked()) {
						b.setBackgroundResource(R.drawable.btn_ok_selector);
						b.setEnabled(true);
					} else {
						b.setEnabled(false);
						b.setBackgroundResource(R.drawable.btn_cancel);
					}
				}
			});
			mycheckBox.setChecked(false);
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			// set the content message
			// if (message != null) {
			// ((TextView) layout.findViewById(R.id.message)).setText(message);
			// } else if (contentView != null) {
			// // if no message set
			// // add the contentView to the dialog body
			// ((LinearLayout) layout.findViewById(R.id.content))
			// .removeAllViews();
			// ((LinearLayout) layout.findViewById(R.id.content))
			// .addView(contentView, new
			// LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
			// }
			dialog.setContentView(layout);
			return dialog;
		}
	}

}
