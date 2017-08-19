package com.example.admin.ajs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.ajs.R;
import com.example.admin.ajs.helper.PrefHelper;
import com.example.admin.ajs.utility.Constants;
import com.example.admin.ajs.utility.Utils;

/**
 * Created by Admin on 5/10/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                Utils.printFbKeyHash();
                PrefHelper.getInstance().setLong(PrefHelper.IMAGE_CACHE_FLAG, System.currentTimeMillis());

                if (PrefHelper.getInstance().getBoolean(PrefHelper.IS_LOGIN, false)) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    //intent.putExtra(Constants.IS_FROM_SIGN_UP, false);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
