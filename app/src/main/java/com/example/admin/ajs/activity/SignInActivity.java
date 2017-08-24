package com.example.admin.ajs.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.admin.ajs.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.admin.ajs.R.drawable.username;

/**
 * Created by Admin on 5/11/2017.
 */

public class SignInActivity extends AppCompatActivity implements OnClickEvent, DataObserver {

    //xml components
    private LinearLayout linParentView;
    EditText edtUserName, edtPassword;
    Button btnLogin;
    private String username, password;
    TextView txvForgotPassword;
    LinearLayout Register_account;
    //private DataObserver dataObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();
      //  dataObserver = this;
    }

    public void init() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            btnLogin.setBackgroundResource(R.drawable.drw_button_shape);
        } else {
            btnLogin.setBackgroundResource(R.drawable.drw_button_shape_two);
        }


        edtUserName = (EditText) findViewById(R.id.edt_userName);
        edtUserName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtPassword.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);


        txvForgotPassword = (TextView) findViewById(R.id.txv_forget_pwd);
        txvForgotPassword.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        linParentView = (LinearLayout) findViewById(R.id.parentView);

        Utils.setupOutSideTouchHideKeyboard(linParentView);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {


            case R.id.btn_login:

                if (validateForm()) {

                    Map<String, String> params = new HashMap<>();
                    params.put("op", ApiList.GET_USER);
                    params.put("AuthKey", ApiList.AUTH_KEY);
                    params.put("UserName", username);
                    params.put("Password", password);


                    RestClient.getInstance().post(this, Request.Method.POST, params,
                            ApiList.GET_USER, true, RequestCode.GetUser, this);
                }
                break;


            case R.id.lin_registerAccount:

                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.txv_forget_pwd:

                Intent Forgot = new Intent(this, ForgotPasswordActivity.class);
                startActivity(Forgot);
                break;

        }

    }

    private boolean validateForm() {

        username = edtUserName.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (username.isEmpty()) {
            edtUserName.requestFocus();
            ToastHelper.getInstance().showMessage("Enter Username");
            return false;
        } else if (password.isEmpty()) {
            edtPassword.requestFocus();
            ToastHelper.getInstance().showMessage("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {

        switch (mRequestCode) {

            case GetUser:

                PrefHelper.getInstance().setBoolean(PrefHelper.IS_LOGIN, true);

                Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
                break;

        }

    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }


}
