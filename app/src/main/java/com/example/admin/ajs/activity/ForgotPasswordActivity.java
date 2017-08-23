package com.example.admin.ajs.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.admin.ajs.helper.ToastHelper;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.utility.Utils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABC on 8/11/2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements OnClickEvent, DataObserver, OnBackPressedEvent {

    private Button btnSave;
    private EditText editUserName;
    private LinearLayout ParentView;
    private String userNmae;
    private ProfileActivity profileActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);
        init();
        Utils.setupOutSideTouchHideKeyboard(findViewById(R.id.parentView));
    }

    private void init() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);



        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            btnSave.setBackgroundResource(R.drawable.drw_button_shape);
        } else {
            btnSave.setBackgroundResource(R.drawable.drw_button_shape_two);
        }


        editUserName = (EditText) findViewById(R.id.edt_userName);
        editUserName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        //ParentView = (LinearLayout) findViewById(R.id.parentView);

       // Utils.setupOutSideTouchHideKeyboard(ParentView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSave:

                if (validateLoginForm()) {
                    Map<String, String> params = new HashMap<>();

                    //JSONObject params = new JSONObject();
                    params.put("op", ApiList.FORGOT_PASSWORD);
                    params.put("AuthKey", ApiList.AUTH_KEY);
                    params.put("UserName", userNmae);

                    RestClient.getInstance().post(this, Request.Method.POST, params,
                            ApiList.FORGOT_PASSWORD, true, RequestCode.ForgotPassword, this);
                }
                break;

            case R.id.img_back_header:
                finish();
                break;

        }
    }

    private boolean validateLoginForm() {
        userNmae = editUserName.getText().toString().trim();

        if (userNmae.isEmpty()) {
            editUserName.requestFocus();
            editUserName.setError("Enter User Name");
            return false;

        } else {return true;

        }
    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {
        switch (mRequestCode) {

            case ForgotPassword:
                ToastHelper.getInstance().showMessage(getString(R.string.str_success));
                finish();
        }
    }
}


