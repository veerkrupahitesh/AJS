package com.example.admin.ajs.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.admin.ajs.api.DataObserver;
import com.example.admin.ajs.api.RequestCode;
import com.example.admin.ajs.api.ResponseStatus;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;

/**
 * Created by ABC on 8/23/2017.
 */

public class BalanceAmountFragment extends Fragment implements OnClickEvent, DataObserver, OnBackPressedEvent {


    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {

    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {

    }
}
