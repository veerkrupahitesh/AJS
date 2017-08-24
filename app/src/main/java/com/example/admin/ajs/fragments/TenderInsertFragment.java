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
import com.example.admin.ajs.activity.HomeActivity;
import com.example.admin.ajs.activity.ProfileActivity;
import com.example.admin.ajs.api.ApiList;
import com.example.admin.ajs.api.DataObserver;
import com.example.admin.ajs.api.RequestCode;
import com.example.admin.ajs.api.ResponseStatus;
import com.example.admin.ajs.api.RestClient;
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
 * Created by ABC on 8/17/2017.
 */

public class TenderInsertFragment extends Fragment implements OnClickEvent, DataObserver, OnBackPressedEvent {


    // xml components
    private LinearLayout linParentView;
    private Toolbar toolbar;
    private TextView tvHeader;
    private Button btnSave;
    private EditText edtTenderNo;

    private String remark;
    private View rootView;
    private Bundle bundle;

    // object and variable declaration
    private JSONObject params;
    private ProfileActivity profileActivity;
    private HomeActivity HomeActivity;
    // private int loginUser;
    LoginUserModel loginUserObject;

    private String TenderNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginUserObject = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));

       // homeActivity = (HomeActivity) getActivity();
        //profileActivity = (ProfileActivity) getActivity();
        HomeActivity = (HomeActivity) getActivity();

        bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tender_insert, container, false);

        edtTenderNo = (EditText) rootView.findViewById(R.id.edt_Tender_no);
        edtTenderNo.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);


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
    public void onSuccess(RequestCode mRequestCode, Object mObject) {
        switch (mRequestCode) {

            case TenderInsert:

                ToastHelper.getInstance().showMessage("Tender Insert Successfully");
                //HomeActivity.popBackFragment();
                break;
        }

    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }

    @Override
    public void onBackPressed() {
        HomeActivity.popBackFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_back_header:
                HomeActivity.popBackFragment();
                break;

            case R.id.btnSave:

                Utils.buttonClickEffect(view);
                InsertTenderNo();
                break;

        }
    }

    private void InsertTenderNo() {
        String TenderNo = edtTenderNo.getText().toString();

        if (TenderNo.isEmpty()) {
            edtTenderNo.requestFocus();
            ToastHelper.getInstance().showMessage("Enter Tender No");
            return;
        }

    try {
        Map<String, String> params = new HashMap<>();
        params.put("op", ApiList.TENDER_INSERT);
        params.put("AuthKey", ApiList.AUTH_KEY);
        params.put("TenderNo",TenderNo);
        params.put("ClientId", String.valueOf(loginUserObject.getClientId()));


        RestClient.getInstance().post(getActivity(), Request.Method.POST, params, ApiList.TENDER_INSERT,
                true, RequestCode.TenderInsert, this);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}