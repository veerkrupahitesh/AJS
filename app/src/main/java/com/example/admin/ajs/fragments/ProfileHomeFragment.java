package com.example.admin.ajs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
import com.example.admin.ajs.activity.ProfileActivity;
import com.example.admin.ajs.activity.SignInActivity;
import com.example.admin.ajs.api.DataObserver;
import com.example.admin.ajs.api.RequestCode;
import com.example.admin.ajs.api.ResponseStatus;
import com.example.admin.ajs.enums.RegisterBy;
import com.example.admin.ajs.helper.PrefHelper;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.model.LoginUserModel;
import com.example.admin.ajs.utility.Utils;


/**
 * Created by VEER7 on 7/4/2017.
 */

public class ProfileHomeFragment extends Fragment implements OnClickEvent, OnBackPressedEvent, DataObserver {

    private View rootView;
    private ImageView imgProfilePhoto;
    private TextView  tvMyProfile, tvBalance, tvRequestTender, tvSearch, tvSignOut,tvSettings;

    private ProfileActivity profileActivity;
    private LoginUserModel loginUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileActivity = (ProfileActivity) getActivity();
       loginUser = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_home, container, false);

        tvBalance = (TextView) rootView.findViewById(R.id.tv_balance);
        tvBalance.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        tvRequestTender = (TextView) rootView.findViewById(R.id.tv_requestTender);
        tvRequestTender.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

//        tvSearch = (TextView) rootView.findViewById(R.id.tv_search);
//        tvSearch.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        tvSettings = (TextView) rootView.findViewById(R.id.tv_settings);
        tvSettings.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        tvMyProfile = (TextView) rootView.findViewById(R.id.tv_myProfile);
        tvMyProfile.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        tvSignOut = (TextView) rootView.findViewById(R.id.tv_signOut);
        tvSignOut.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {

    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {

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

            case R.id.tv_myProfile:

                profileActivity.pushFragment(new MyProfileFragment(), true, false, null);
                break;

//            case R.id.tv_balance:
//
//                profileActivity.pushFragment(new ReviewRatingFragment(), true, false, null);
//                break;
//
            case R.id.tv_requestTender:
                profileActivity.pushFragment(new TenderInsertFragment(), true, false, null);
                break;
//
//            case R.id.tv_search:
//
//                profileActivity.pushFragment(new PackagesFragment(), true, false, null);
//
//                break;


            case R.id.tv_settings:

                profileActivity.pushFragment(new SettingFragment(), true, false, null);
                break;


            case R.id.tv_signOut:

                LoginUserModel loginUser = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));

//                if (loginUser != null)
//
//                    if (loginUser.getRegisteredBy().equals(RegisterBy.APP.getRegisterBy())) {

                PrefHelper.getInstance().clearAllPrefs();
                Intent intent = new Intent(profileActivity, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the stack of activities
                startActivity(intent);
                profileActivity.finish();

//                    } else if (loginUser.getRegisteredBy().equals(RegisterBy.FACEBOOK.getRegisterBy())) {
//
//                        PrefHelper.getInstance().clearAllPrefs();
//                        SignInActivity.logoutToFacebook();
//                        Intent intent = new Intent(profileActivity, SignInActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the stack of activities
//                        startActivity(intent);
//                        profileActivity.finish();
//                    }
//                break;
        }
        }
}
