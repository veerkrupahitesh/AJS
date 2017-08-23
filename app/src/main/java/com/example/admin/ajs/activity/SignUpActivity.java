package com.example.admin.ajs.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
import com.example.admin.ajs.api.ApiList;
import com.example.admin.ajs.api.DataObserver;
import com.example.admin.ajs.api.RequestCode;
import com.example.admin.ajs.api.ResponseStatus;
import com.example.admin.ajs.api.RestClient;
import com.example.admin.ajs.helper.PrefHelper;
import com.example.admin.ajs.helper.ToastHelper;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.utility.Constants;
import com.example.admin.ajs.utility.Utils;

import java.util.HashMap;
import java.util.Map;

import static com.example.admin.ajs.api.RequestCode.clientInsert;

public class SignUpActivity extends AppCompatActivity implements OnClickEvent,DataObserver {

    // xml components
    private LinearLayout linParentView;
    private EditText edtFristName,edtLastName, edtCompanyName,edtEmail, edtPhone, edtUserName, edtPassword;
    private Button btnSingUp;
    private DataObserver dataObserver;

    //object or variable declaration
    private String mFristName, mLastName,mCompanyName, mEmailAddress, phoneNumber, mPassword,mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        init();
       // dataObserver = this;
        Utils.setupOutSideTouchHideKeyboard(linParentView);
    }

    private void init() {

        btnSingUp = (Button) findViewById(R.id.btn_singup);
        btnSingUp.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            btnSingUp.setBackgroundResource(R.drawable.drw_button_shape);
        } else {
            btnSingUp.setBackgroundResource(R.drawable.drw_button_shape_two);
        }

        edtFristName = (EditText) findViewById(R.id.edt_FristName);
        edtFristName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtLastName = (EditText)findViewById(R.id.edt_LastName);
        edtLastName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtCompanyName = (EditText)findViewById(R.id.edt_company_name);
        edtCompanyName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtEmail.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtPhone.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtUserName = (EditText) findViewById(R.id.edt_username);
        edtUserName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtPassword.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        linParentView = (LinearLayout) findViewById(R.id.parentView);

        Utils.setupOutSideTouchHideKeyboard(linParentView);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_singup:

                if (validateForm()) {
                    try {
                        //params = new JSONObject();
                        Map<String, String> params = new HashMap<>();
                        params.put("op", ApiList.CLIENT_INSERT);
                        params.put("AuthKey", ApiList.AUTH_KEY);

                        params.put("FirstName", mFristName);
                        params.put("LastName", mLastName);
                        params.put("CompanyName",mCompanyName);
                        params.put("MobileNo",phoneNumber);
                        params.put("EmailId", mEmailAddress);
                        params.put("UserName",mUserName);
                        params.put("Password", mPassword);


                        RestClient.getInstance().post(this, Request.Method.POST, params, ApiList.CLIENT_INSERT,
                                true, RequestCode.clientInsert, this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private boolean validateForm() {


        mFristName = edtFristName.getText().toString().trim();
        mLastName = edtLastName.getText().toString().trim();
        mCompanyName = edtCompanyName.getText().toString().trim();
        mEmailAddress = edtEmail.getText().toString().trim();
        phoneNumber = edtPhone.getText().toString().trim();
        mUserName = edtUserName.getText().toString().trim();
        mPassword = edtPassword.getText().toString().trim();
//

        if (mFristName.isEmpty()) {
            edtFristName.requestFocus();
            edtFristName.setError("Enter Frist Name");
            return false;
        } else if (mLastName.isEmpty()) {
            edtLastName.requestFocus();
            edtLastName.setError("Enter Last Name");
            return false;
        } else if (mCompanyName.isEmpty()) {
            edtCompanyName.requestFocus();
            edtCompanyName.setError("Enter Company Name");
            return false;
        } else if (mEmailAddress.isEmpty()) {
            edtEmail.requestFocus();
            edtEmail.setError("Enter email address");
            return false;
        } else if (!mEmailAddress.matches(Patterns.EMAIL_ADDRESS.pattern())) {
            edtEmail.requestFocus();
            edtEmail.setError("Enter valid email address");
            return false;
        } else if (phoneNumber.isEmpty()) {
            edtPhone.requestFocus();
            edtPhone.setError("Enter phone number");
            return false;
        } else if (phoneNumber.length() != Constants.PHONE_LENGTH) {
            edtPhone.requestFocus();
            edtPhone.setError("Enter valid phone number");
            return false;
        } else if (mUserName.isEmpty()) {
            edtUserName.requestFocus();
            edtUserName.setError("Enter Username");
            return false;
        } else if (mPassword.isEmpty()) {
            edtPassword.requestFocus();
            edtPassword.setError("Enter password");
            return false;
        } else {        return true;
        }

    }

    public void onSuccess(RequestCode mRequestCode, Object mObject) {


        switch (mRequestCode) {

            case clientInsert:

                PrefHelper.getInstance().setBoolean(PrefHelper.IS_LOGIN, true);
                Intent intent = new Intent(this, HomeActivity.class);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the stack of activities
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }


}
