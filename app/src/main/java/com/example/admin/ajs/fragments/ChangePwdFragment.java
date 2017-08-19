package com.example.admin.ajs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
import com.example.admin.ajs.activity.ProfileActivity;
import com.example.admin.ajs.api.ApiList;
import com.example.admin.ajs.api.DataObserver;
import com.example.admin.ajs.api.RequestCode;
import com.example.admin.ajs.api.ResponseStatus;
import com.example.admin.ajs.api.RestClient;
import com.example.admin.ajs.customdialog.CustomDialog;
import com.example.admin.ajs.helper.PrefHelper;
import com.example.admin.ajs.helper.ToastHelper;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.model.LoginUserModel;
import com.example.admin.ajs.utility.Utils;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaishali on 7/12/2017.
 */

public class ChangePwdFragment extends Fragment implements OnClickEvent, DataObserver, OnBackPressedEvent {


    // xml components
    private LinearLayout linParentView;
    private Toolbar toolbar;
    private TextView tvHeader;
    private Button btnSave;
    private EditText edtOldPwd, edtNewPwd, edtConfirmPwd;

    private String remark;
    private View rootView;
    private Bundle bundle;

    // object and variable declaration
    private JSONObject params;
    private ProfileActivity profileActivity;
    // private int loginUser;
    LoginUserModel loginUserObject;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileActivity = (ProfileActivity) getActivity();
        bundle = getArguments();
        loginUserObject = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_change_pwd, container, false);

        edtOldPwd = (EditText) rootView.findViewById(R.id.edt_old_pwd);
        edtOldPwd.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtNewPwd = (EditText) rootView.findViewById(R.id.edt_new_pwd);
        edtNewPwd.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        edtConfirmPwd = (EditText) rootView.findViewById(R.id.edt_confirm_pwd);
        edtConfirmPwd.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        return rootView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Utils.setupOutSideTouchHideKeyboard(rootView);
    }


    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }

    @Override
    public void onBackPressed() {
        profileActivity.popBackFragment();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_back_header:
                profileActivity.popBackFragment();
                break;

            case R.id.btnSave:

                Utils.buttonClickEffect(view);
                ClientChangePassword();
                break;

            case R.id.btn_alertOk:

                Utils.buttonClickEffect(view);
                CustomDialog.getInstance().dismiss();
                break;
        }
    }

    private void ClientChangePassword() {

        String OldPwd = edtOldPwd.getText().toString();
        String NewPwd = edtNewPwd.getText().toString();
        String ConfirmPwd = edtConfirmPwd.getText().toString();

        if (OldPwd.isEmpty()) {
            edtOldPwd.requestFocus();
            edtOldPwd.setError("Enter OldPassWord");
            return;
        }

        if (NewPwd.isEmpty()) {
            edtNewPwd.requestFocus();
            edtNewPwd.setError("Enter NewPassWord");
            return;
        }

        if (ConfirmPwd.isEmpty()) {
            edtConfirmPwd.requestFocus();
            edtConfirmPwd.setError("Enter Re-EnterPassWord");
            return;
        }

        if (!ConfirmPwd.equals(NewPwd)) {
            edtConfirmPwd.requestFocus();
            ToastHelper.getInstance().showMessage("password and confirm password does not matched");
            return;
        }

        if (!OldPwd.equals(loginUserObject.getPassword())) {

            edtOldPwd.requestFocus();
            edtOldPwd.setError("Old password is invalid");
            return;
        }
        try {
            Map<String, String> params = new HashMap<>();
            params.put("op", ApiList.CLIENT_CHANGE_PASSWORD);
            params.put("AuthKey", ApiList.AUTH_KEY);
            params.put("ClientId", String.valueOf(loginUserObject.getClientId()));
            // params.put("JobPostId",String.valueOf(loginUserObject.getClientId()));
            params.put("Password", ConfirmPwd);

            RestClient.getInstance().post(getActivity(), Request.Method.POST, params, ApiList.CLIENT_CHANGE_PASSWORD,
                    true, RequestCode.ClientChangePassword, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {
        switch (mRequestCode) {

            case ClientChangePassword:

                ToastHelper.getInstance().showMessage("PassWord Change Successfully");
                profileActivity.popBackFragment();
                break;
        }
    }
}


