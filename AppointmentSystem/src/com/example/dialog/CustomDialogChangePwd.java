package com.example.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fragmentmytest.R;

public class CustomDialogChangePwd extends Dialog {
	public CustomDialogChangePwd(Context context) {  
        super(context);  
    }  
  
    public CustomDialogChangePwd(Context context, int theme) {  
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
  
        private EditText stuId,stuPwd,stuPwdNew;
        
        
        
        public String getUserId() {
        	if(stuId!=null){
        		return stuId.getText().toString();
        	}else{
        		return "stuId is null!";
        	}
		}

        public String getPwdNew() {
        	if(stuPwdNew!=null){
        		return stuPwdNew.getText().toString();
        	}else{
        		return "stuId is null!";
        	}
		}
        
        

		public String getPassword() {
			if(stuPwd!=null){
        		return stuPwd.getText().toString();
        	}else{
        		return "stuPwd is null!";
        	}
		}
		
		
		public EditText getStuIdEditText(){
			if(stuId!=null){
        		return stuId;
        	}else{
        		return null;
        	}
		}
		
		public EditText getStuPwdEditText(){
			if(stuPwd!=null){
        		return stuPwd;
        	}else{
        		return null;
        	}
		}
		
		public EditText getStuPwdNewEditText(){
			if(stuPwdNew!=null){
        		return stuPwdNew;
        	}else{
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
  
        public CustomDialogChangePwd create() {  
            LayoutInflater inflater = (LayoutInflater) context  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            // instantiate the dialog with the custom Theme  
            final CustomDialogChangePwd dialog = new CustomDialogChangePwd(context,R.style.Dialog);  
            View layout = inflater.inflate(R.layout.dialog_normal_layout_change_pwd, null);  
            dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));  
            // set the dialog title  
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            
            stuId = (EditText) layout.findViewById(R.id.stuId);
            stuPwd = (EditText) layout.findViewById(R.id.stuPwd);
            stuPwdNew = (EditText) layout.findViewById(R.id.stuPwdNew);
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
            dialog.setContentView(layout);  
            return dialog;  
        }  
    }  
}
