package com.example.admin.ajs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.admin.ajs.MyApplication;

import com.example.admin.ajs.R;
import com.example.admin.ajs.activity.HomeActivity;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ABC on 8/23/2017.
 */

public class BalanceAmountFragment extends Fragment implements OnClickEvent, DataObserver, OnBackPressedEvent {

    private TextView tvBalanceAmount;
    //  private LoginUserModel loginUserObject;
    private Bundle bundle;
    private LoginUserModel loginUserObject;
    private HomeActivity homeActivity;
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivity = (HomeActivity) getActivity();
        //bundle = getArguments();
        loginUserObject = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_balance, container, false);

        tvBalanceAmount = (TextView) rootView.findViewById(R.id.txv_balance_amount);
        tvBalanceAmount.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

//        tvBalanceAmount.setText(loginUserModel.getBalanceTender());

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetClientInfo();
    }

    private void GetClientInfo() {

        try {

            Map<String, String> params = new HashMap<>();
            params.put("op", ApiList.GET_CLIENT_INFO);
            params.put("ClientId", String.valueOf(loginUserObject.getClientId()));
            params.put("AuthKey", ApiList.AUTH_KEY);

            RestClient.getInstance().post(getActivity(), Request.Method.POST, params, ApiList.GET_CLIENT_INFO,
                    true, RequestCode.GetClientInfo, this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {
        switch (mRequestCode) {

            case GetClientInfo:

                if (mObject instanceof LoginUserModel) {

                    loginUserObject = (LoginUserModel) mObject;
                    tvBalanceAmount.setText(String.valueOf(loginUserObject.getBalanceTender()+ " ₹ "));

                }
                break;

        }
    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }

    @Override
    public void onBackPressed() {
        homeActivity.popBackFragment();
    }
    //


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_back_header:

                homeActivity.popBackFragment();
                break;
        }
    }
}