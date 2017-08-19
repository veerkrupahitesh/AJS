package com.example.admin.ajs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.admin.ajs.R;
import com.example.admin.ajs.fragments.ProfileHomeFragment;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;


public class ProfileActivity extends AppCompatActivity implements OnClickEvent, OnBackPressedEvent {

    OnClickEvent onClickEvent;
    OnBackPressedEvent onBackPressedEvent;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fragmentManager = getSupportFragmentManager();

        pushFragment(new ProfileHomeFragment(), true, false, null);
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

    @Override
    public void onClick(View view) {

        onClickEvent.onClick(view);
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedEvent != null) {
            onBackPressedEvent.onBackPressed();
        } else {
            finish();
        }
       /* if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {

            }
        }*/
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
