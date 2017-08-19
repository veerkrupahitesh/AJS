package com.example.admin.ajs.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
import com.example.admin.ajs.activity.HomeActivity;
import com.example.admin.ajs.activity.ProfileActivity;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.utility.Utils;


public class SettingFragment extends Fragment implements OnClickEvent, OnBackPressedEvent {

    // xml components
    private Toolbar toolbar;
    private TextView tvHeader, tvRateApp, tvShareApp, tvChangePassword, tvAboutUs;
    private View rootView;

    // object and variable declaration
    private ProfileActivity profileActivity;
    private HomeActivity homeActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
      //  homeActivity = (HomeActivity) getActivity();
        profileActivity = (ProfileActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        tvRateApp = (TextView) rootView.findViewById(R.id.tv_rateApp);
        tvRateApp.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        tvShareApp = (TextView) rootView.findViewById(R.id.tv_shareApp);
        tvShareApp.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        tvChangePassword = (TextView) rootView.findViewById(R.id.tv_changePassword);
        tvChangePassword.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        tvAboutUs = (TextView) rootView.findViewById(R.id.tv_aboutUs);
        tvAboutUs.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // backButton = (ImageView) getView().findViewById(R.id.img_back_header);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_back_header:

                profileActivity.popBackFragment();
                break;

            case R.id.lin_change_password:

                Utils.buttonClickEffect(view);
                profileActivity.pushFragment(new ChangePwdFragment(), true, true, null);
                break;

            case R.id.lin_shareApp:

//                shareApp();
                break;

            case R.id.lin_rate_app:

//                goToPlayStore();
                break;

            case R.id.lin_aboutUs:
                Utils.buttonClickEffect(view);
                profileActivity.pushFragment(new AboutUsFragment(), true, true, null);
                break;

        }
    }



    @Override
    public void onBackPressed() {
        profileActivity.popBackFragment();

    }

    private void shareApp() {
        String shareBody = "https://play.google.com/store/apps/details?id=" + profileActivity.getPackageName();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "AniVetHub  (Open it in Google Play Store to Download the Application)");

        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    private void goToPlayStore() {

        final String appPackageName = profileActivity.getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
