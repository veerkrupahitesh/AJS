package com.example.admin.ajs.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
import com.example.admin.ajs.activity.HomeActivity;
import com.example.admin.ajs.activity.ProfileActivity;
import com.example.admin.ajs.helper.PrefHelper;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.utility.Utils;


/**
 * Created by Veer on 28/09/2016.
 */
public class AboutUsFragment extends Fragment implements OnBackPressedEvent, OnClickEvent {

    String fbUrl = "https://www.facebook.com/anivethub";
    String twitterUrl = "https://twitter.com/anivethub";
    String gplusUrl = "https://plus.google.com/u/0/113707138210597096384";
    // xml components
    private ProfileActivity profileActivity;
    private Toolbar toolbar;
    private TextView tvHeader, tvAboutUsDetails;
    private HomeActivity homeActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        profileActivity = (ProfileActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        tvAboutUsDetails = (TextView) getView().findViewById(R.id.tv_aboutUsDetails);
        tvAboutUsDetails.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_back_header:

                profileActivity.popBackFragment();
                break;

            case R.id.img_fb:
                Utils.buttonClickEffect(view);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fbUrl));
                startActivity(i);
                break;

            case R.id.img_twitter:
                Utils.buttonClickEffect(view);

                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(twitterUrl));
                startActivity(i1);


            case R.id.img_gplus:
                Utils.buttonClickEffect(view);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(gplusUrl));
                startActivity(intent);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        profileActivity.popBackFragment();
    }
}
