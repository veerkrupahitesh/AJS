package com.example.admin.ajs;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin.ajs.utility.Constants;

/**
 * Created by Admin on 5/11/2017.
 */

public class MyApplication extends Application {


    public static final String TAG = MyApplication.class.getSimpleName();
    public static boolean isAppRunnning;
    private static MyApplication mInstance;
    public final int VOLLEY_TIMEOUT = 60000;
    public Typeface  FONT_WORKSANS_MEDIUM, FONT_WORKSANS_REGULAR;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        FONT_WORKSANS_MEDIUM = getTypeFace(Constants.FONT_WORKSANS_MEDIUM);
        FONT_WORKSANS_REGULAR = getTypeFace(Constants.FONT_WORKSANS_REGULAR);
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public Typeface getTypeFace(String typeFaceName) {
        return Typeface.createFromAsset(getAssets(), typeFaceName);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

