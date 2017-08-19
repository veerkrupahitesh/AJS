package com.example.admin.ajs.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.admin.ajs.R;
import com.example.admin.ajs.fragments.HomeFragment;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.utility.Utils;

import java.util.ArrayList;


/**
 * Created by Admin on 5/11/2017.
 */

public class HomeActivity extends AppCompatActivity implements OnClickEvent, OnBackPressedEvent/*, NavigationView.OnNavigationItemSelectedListener*/
        , View.OnClickListener {

    OnClickEvent onClickEvent;
    OnBackPressedEvent onBackPressedEvent;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment currentFragment;
    private Bundle bundle;

    //private NavigationView navigationView;
    // private TextView navHeaderName, navHeaderLocation, navRating, navPoint, navHelpme, navOffered, navRatingText, navPointText, navHelpmeText, navOfferedText;
    //private DrawerLayout drawer;
    //private ActionBarDrawerToggle toggle;
    private LinearLayout linMyProfile, linTenderInsert, linRequestTender, linsetting;

    private Location mLastLocation;
    private ArrayList<String> permission;
    private float lat, lon, altitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        permission = new ArrayList<>();
        permission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permission.add(Manifest.permission.ACCESS_NETWORK_STATE);

        fragmentManager = getSupportFragmentManager();

        pushFragment(new HomeFragment(), true, false, null);
    }

    private void init() {

        linMyProfile = (LinearLayout) findViewById(R.id.lin_my_profile);
        linMyProfile.setOnClickListener(this);
        linTenderInsert = (LinearLayout) findViewById(R.id.lin_tender_insert);
        linTenderInsert.setOnClickListener(this);
        linRequestTender = (LinearLayout) findViewById(R.id.lin_download_file);
        linRequestTender.setOnClickListener(this);
        linsetting = (LinearLayout) findViewById(R.id.lin_setting);
        linsetting.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        onClickEvent.onClick(view);
        switch (view.getId()) {

            //case R.id.img_helpMe:

//                Utils.buttonClickEffect(view);
//                if (!(currentFragment instanceof ProfileHomeFragment)) {
//                    removeAllFragment();
//                    bundle = new Bundle();
//                    bundle.putInt(Constants.IS_FROM_HOME_ACTIVITY, 0);
//                    pushFragment(new ProfileHomeFragment(), true, false, bundle);
            //}

            // break;

           /* case R.id.img_back_header:
                Utils.buttonClickEffect(view);
                if (drawer != null) {
                    if (!drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
                break;*/

//            case R.id.lin_home:
//                Utils.buttonClickEffect(view);
//                if (!(currentFragment instanceof HomeFragment)) {
//                    removeAllFragment();
//                    pushFragment(new HomeFragment(), true, false, null);
//                }
//                break;
//
//            case R.id.lin_fab_search:
//
//                Utils.buttonClickEffect(view);
//                if (!(currentFragment instanceof SearchFragment)) {
//                    removeAllFragment();
//                    pushFragment(new SearchFragment(), true, false, null);
//                }
//                break;
////
//            case R.id.lin_tender_insert:
//
//                Utils.buttonClickEffect(view);
//                Intent intent1 = new Intent(this, MainActivity.class);
//                startActivity(intent1);
//                break;

            case R.id.lin_download_file:

                Utils.buttonClickEffect(view);
                Intent intent = new Intent(this, FileDownloadActivity.class);
                startActivity(intent);
                break;

            case R.id.lin_my_profile:
                Utils.buttonClickEffect(view);
                //imgUserProfile.setEnabled(false);
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedEvent != null) {
            onBackPressedEvent.onBackPressed();
        }
       /* if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {

            }
        }*/
    }


    public void pushFragment(Fragment fragment, boolean addToBackStack, boolean shouldAnimate, Bundle bundle) {

        currentFragment = fragment;
        onClickEvent = (OnClickEvent) fragment;
        onBackPressedEvent = (OnBackPressedEvent) fragment;

        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction = fragmentManager.beginTransaction();

        if (shouldAnimate) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getCanonicalName());
        }

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        fragmentTransaction.replace(R.id.container, fragment, fragment.getClass().getCanonicalName());

        //currentFragment = (Fragment) onClickEvent;

        // Commit the transaction
        fragmentTransaction.commit();

    }

    public void popBackFragment() {

        try {
            int backStackCount = fragmentManager.getBackStackEntryCount();

            if (backStackCount > 1) {

                FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(backStackCount - 2);

                String className = backStackEntry.getName();

                Fragment fragment = fragmentManager.findFragmentByTag(className);

                currentFragment = fragment;
                onClickEvent = (OnClickEvent) fragment;
                onBackPressedEvent = (OnBackPressedEvent) fragment;

                fragmentManager.popBackStack();
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Remove all fragments from back stack*/
    public void removeAllFragment() {

        int fragmentsCount = fragmentManager.getBackStackEntryCount();

        if (fragmentsCount > 0) {
            // MyApplication.disableFragmentAnimations = true;
            FragmentTransaction ft = fragmentManager.beginTransaction();
            //		manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.commit();

            // MyApplication.disableFragmentAnimations = false;
            //fragmentManager.popBackStack("myfancyname", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        //removeFragmentUntil(DrinkListFragment);
    }

    /*Remove Fragments until provided Fragment class*/
    public void removeFragmentUntil(Class<?> fragmentClass) {

        try {
            int backStackCountMain = fragmentManager.getBackStackEntryCount();
            if (backStackCountMain > 1) {
                /*Note: To eliminate pop menu fragments and push base menu fragment animation effect at a same times*/
                //MyApplication.disableFragmentAnimations = true;
                int backStackCount = backStackCountMain;
                for (int i = 0; i < backStackCountMain; i++) {
                    FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(backStackCount - 1);
                    String str = backEntry.getName();
                    Fragment fragment = fragmentManager.findFragmentByTag(str);
                    if (fragment.getClass().getCanonicalName().equals(fragmentClass.getCanonicalName())) {
                        currentFragment = fragment;
                        onClickEvent = (OnClickEvent) fragment;
                        onBackPressedEvent = (OnBackPressedEvent) fragment;
                        break;
                    } else
                        fragmentManager.popBackStack();

                    backStackCount--;
                }

            } else
                finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused LocationModel Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */


}
