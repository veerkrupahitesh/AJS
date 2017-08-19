package com.example.admin.ajs.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
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
import com.example.admin.ajs.model.CityModel;
import com.example.admin.ajs.model.CountryModel;
import com.example.admin.ajs.model.LoginUserModel;
import com.example.admin.ajs.model.StateModel;
import com.example.admin.ajs.utility.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Admin on 6/28/2017.
 */

public class MyProfileFragment extends Fragment implements OnBackPressedEvent, OnClickEvent, DataObserver {


    private View rootView;
    private EditText edtFirstName, edtLastName, edtEmail, edtTelephone, edtPostalCode, edtCompanyName;
    private Button btnsubmit;
    private LoginUserModel loginUserObject;
    private Spinner spgender;
    private ImageView imgProfilePhoto, imgBannerPhoto;
    private TextView tvProfileInfo, tvPersonalInfo, edtCity,
            edtState, edtCountry;
    private ProgressBar prgBanner, prgProfile;

    private ProfileActivity profileActivity;
    private List<String> genderList;
    private ArrayList<CountryModel> countryList;
    private ArrayList<StateModel> stateList;
    private ArrayList<CityModel> cityList;
    private Dialog mDialog;
    private String countryId = "", stateId = "", cityId = "";
    private List<String> permissionList;
    private String image64Base = "";

    private String firstName, lastName, emailId, companyName, mobileNo, Country, State, City, poBox, password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileActivity = (ProfileActivity) getActivity();
        loginUserObject = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        tvPersonalInfo = (TextView) rootView.findViewById(R.id.tv_personalInfo);
        tvPersonalInfo.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        tvProfileInfo = (TextView) rootView.findViewById(R.id.tv_profileInfo);
        tvProfileInfo.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtFirstName = (EditText) rootView.findViewById(R.id.edt_firstName);
        edtFirstName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtLastName = (EditText) rootView.findViewById(R.id.edt_lastName);
        edtLastName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtCompanyName = (EditText) rootView.findViewById(R.id.edt_CompanyName);
        edtCompanyName.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtEmail = (EditText) rootView.findViewById(R.id.edt_email);
        edtEmail.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

//        edtPassword = (EditText) rootView.findViewById(R.id.edt_password);
//        edtPassword.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtTelephone = (EditText) rootView.findViewById(R.id.edt_telephone);
        edtTelephone.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtCity = (TextView) rootView.findViewById(R.id.edt_city);
        edtCity.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtState = (TextView) rootView.findViewById(R.id.edt_state);
        edtState.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtCountry = (TextView) rootView.findViewById(R.id.edt_country);
        edtCountry.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        edtPostalCode = (EditText) rootView.findViewById(R.id.edt_postalCode);
        edtPostalCode.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        btnsubmit = (Button) rootView.findViewById(R.id.btn_update);
        btnsubmit.setTypeface(MyApplication.getInstance().FONT_WORKSANS_MEDIUM);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetClientInfo();
    }

    @Override
    public void onBackPressed() {

        profileActivity.popBackFragment();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

//            case R.id.img_helpMe:
//
//                break;

            case R.id.btn_update:

                if (validateForm()) {

                    clientUpdate();
                }

                break;

            case R.id.img_back_header:

                profileActivity.popBackFragment();
                break;


        }
    }

    private boolean validateForm() {

        firstName = edtFirstName.getText().toString().trim();
        lastName = edtLastName.getText().toString().trim();
        emailId = edtEmail.getText().toString().trim();
//        password = edtPassword.getText().toString().trim();
        companyName = edtCompanyName.getText().toString().trim();
        mobileNo = edtTelephone.getText().toString().trim();
        poBox = edtPostalCode.getText().toString().trim();

        if (firstName.isEmpty()) {
            edtFirstName.setError("First name can not be empty");
            return false;
        } else if (lastName.isEmpty()) {
            edtLastName.setError("Last name can not be empty");
            return false;
        } else if (emailId.isEmpty()) {
            edtEmail.setError("Email address can not be empty");
            return false;
//        } else if (password.isEmpty()) {
//            edtPassword.setError("Password can not be empty");
//            return false;

        } else if (companyName.isEmpty()) {
            edtCompanyName.setError("CompanyName can not be empty");
            return false;
        } else if (mobileNo.isEmpty()) {
            edtTelephone.setError("MobileNo can not be empty");
            return false;

        } else if (poBox.isEmpty()) {
            edtPostalCode.setError("poBox can not be empty");
            return false;

        } else {

            return true;
        }
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

    private void clientUpdate() {

        try {

            Map<String, String> params = new HashMap<>();
            params.put("op", ApiList.CLIENT_UPDATE);
            params.put("AuthKey", ApiList.AUTH_KEY);
            params.put("ClientId", String.valueOf(loginUserObject.getClientId()));
            params.put("FirstName", firstName);
            params.put("LastName", lastName);
            params.put("CompanyName", companyName);
            params.put("Address1", "");
            params.put("Address2", "");
            params.put("City", "");
            params.put("POBox", poBox);

            params.put("State", "");
            params.put("Country", "");
            params.put("MobileNo", mobileNo);
            params.put("EmailId", emailId);
            params.put("PhoneNo", "");
            params.put("Extention", "");

            RestClient.getInstance().post(getActivity(), Request.Method.POST, params, ApiList.CLIENT_UPDATE,
                    true, RequestCode.ClientUpdate, this);

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
                    edtFirstName.setText(loginUserObject.getFirstName());
                    edtLastName.setText(loginUserObject.getLastName());
                    edtCompanyName.setText(loginUserObject.getCompanyName());
                    edtEmail.setText(loginUserObject.getEmailId());
//                    edtPassword.setText(loginUserObject.getPassword());
                    edtTelephone.setText(loginUserObject.getMobileNo());
                    // edtCity.setText(loginUserObject.getCity());
                    // edtState.setText(loginUserObject.getState());
                    // edtCountry.setText(loginUserObject.getCountry());
                    edtPostalCode.setText(loginUserObject.getPOBox());

                }
                break;

            case ClientUpdate:

                ToastHelper.getInstance().showMessage("Profile updated");
                profileActivity.popBackFragment();
                break;
        }
    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }
}
