package com.example.admin.ajs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment implements OnClickEvent, DataObserver, OnBackPressedEvent {

    //xml components
    private RecyclerView recyclerViewHelp, recyclerViewCategory;
    private View rootView;
    //  private Toolbar toolbar;
    private ImageView imgUserProfile, imgSearch, imgSearchBackground;
    private EditText edtSearch;
    private RelativeLayout relHomeView, linRecyclerView;
    private LinearLayout linSearchView, linSearch;
    private FloatingActionButton fabSearch, fabCateSearch, fabKeyWordSearch;


    //object and variable declaration

    private HomeActivity homeActivity;
    private Map<String, String> params;

    private int totalPages;

    private LoginUserModel loginUserModel;
    private boolean isSearchClosed = true, isCateClosed = true, isKeywordClosed = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivity = (HomeActivity) getActivity();
        loginUserModel = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_file_download, container, false);

        imgUserProfile = (ImageView) rootView.findViewById(R.id.img_userProfile);
        return rootView;
    }

    @Override
    public void onBackPressed() {

        homeActivity.popBackFragment();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.img_userProfile:

                Utils.buttonClickEffect(view);
                //imgUserProfile.setEnabled(false);
                Intent intent = new Intent(homeActivity, ProfileActivity.class);
                startActivity(intent);
                break;


        }
    }


    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {

        switch (mRequestCode) {


        }
    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }



}
