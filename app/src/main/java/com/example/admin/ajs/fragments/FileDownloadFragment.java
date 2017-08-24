package com.example.admin.ajs.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
import com.example.admin.ajs.activity.HomeActivity;
import com.example.admin.ajs.api.ApiList;
import com.example.admin.ajs.api.DataObserver;
import com.example.admin.ajs.api.InputStreamVolleyRequest;
import com.example.admin.ajs.api.RequestCode;
import com.example.admin.ajs.api.ResponseStatus;
import com.example.admin.ajs.api.RestClient;
import com.example.admin.ajs.customdialog.CustomDialog;
import com.example.admin.ajs.helper.PrefHelper;
import com.example.admin.ajs.helper.ToastHelper;
import com.example.admin.ajs.listener.OnBackPressedEvent;
import com.example.admin.ajs.listener.OnClickEvent;
import com.example.admin.ajs.model.FileModel;
import com.example.admin.ajs.model.LoginUserModel;
import com.example.admin.ajs.utility.PermissionClass;
import com.example.admin.ajs.utility.Utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.ajs.R.id.btn_file_download;

/**
 * Created by ABC on 8/22/2017.
 */

public class FileDownloadFragment extends Fragment implements OnClickEvent, DataObserver, OnBackPressedEvent {

    private HomeActivity HomeActivity;
    LoginUserModel loginUserObject;
   // private Bundle bundle;
    private View rootView;
    private TextView tvFileDate;
    private DatePickerDialog dialog;
    private Calendar mCurrentDate = Calendar.getInstance();
    private int mYear = mCurrentDate.get(Calendar.YEAR);
    private int mMonth = mCurrentDate.get(Calendar.MONTH);
    private int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
    private String Filedate;

    // InputStreamVolleyRequest request;
    int count;
    //String mUrl = "http://books.sonatype.com/mvnref-book/pdf/mvnref-pdf.pdf";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loginUserObject = (LoginUserModel) Utils.stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));

        // homeActivity = (HomeActivity) getActivity();
        //profileActivity = (ProfileActivity) getActivity();
        HomeActivity = (HomeActivity) getActivity();

      //  bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_file_download, container, false);

        tvFileDate = (TextView) rootView.findViewById(R.id.txv_file_date);
        tvFileDate.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Utils.setupOutSideTouchHideKeyboard(rootView);
    }

    @Override
    public void onSuccess(RequestCode mRequestCode, Object mObject) {
        switch (mRequestCode) {
            case GetFileInfo:


                FileModel fileModel = (FileModel) mObject;
                downloadfile(fileModel);
                // ToastHelper.getInstance().showMessage("Download Successfully");
                break;

        }
    }

    private void saveData() {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("op", ApiList.GET_File_INFO);
            params.put("AuthKey", ApiList.AUTH_KEY);
            params.put("FileDate", Filedate);

            RestClient.getInstance().post(getActivity(), Request.Method.POST, params, ApiList.GET_File_INFO,
                    true, RequestCode.GetFileInfo, this);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(RequestCode mRequestCode, ResponseStatus mResponseStatus) {
        ToastHelper.getInstance().showMessage(mResponseStatus.getError());
    }

    @Override
    public void onBackPressed() {
        HomeActivity.popBackFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case btn_file_download:
                Utils.buttonClickEffect(view);
                if (validateForm()) {

                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

                        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permissions, 1);
                       /* if (PermissionClass.checkPermission(getActivity(), 1, permission)) {
                            saveData();
                        }*/
                    } else {
                        saveData();
                    }

                }
                break;

            case R.id.img_back_header:
                HomeActivity.popBackFragment();
                break;


            case R.id.txv_file_date:

                Utils.buttonClickEffect(view);
                dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {

                        // fromDate = (1 + selectedmonth) + "/" + selectedday + "/" + selectedyear;
                        String format = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedday, selectedmonth + 1, selectedyear);
                        tvFileDate.setText(format);
                        //tvPetBirthDate.setText((1 + selectedmonth) + "/" + selectedday + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
                break;
        }
    }


    private boolean validateForm() {
        Filedate = tvFileDate.getText().toString().trim();
        if (Filedate.isEmpty()) {
            tvFileDate.requestFocus();
            ToastHelper.getInstance().showMessage(getString(R.string.str_select_date));
            return false;
        } else {
            return true;
        }
    }

    private void downloadfile(FileModel fileModel) {

        CustomDialog.getInstance().showProgress(getActivity(), "Please wait...", false);
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, fileModel.getFilePath(),
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        //   HashMap<String, Object> map = new HashMap<String, Object>();
                        CustomDialog.getInstance().dismiss();
                        try {
                            if (response != null) {
//                                FileOutputStream outputStream;
//                                String name = "mvnref-pdf.pdf";
//                                outputStream = openFileOutput(name, Context.MODE_PRIVATE);
//                                outputStream.write(response);
//                                outputStream.close();
//                                this.getClass().getName();
                                //covert reponse to input stream
                                InputStream input = new ByteArrayInputStream(response);
                                java.io.File path = Environment.getExternalStorageDirectory();
                                File file = new File(path, "AJS" + System.currentTimeMillis() + ".pdf");
                                //       map.put("resume_path", file.toString());
                                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                                byte data[] = new byte[1024];

                                long total = 0;

                                while ((count = input.read(data)) != -1) {
                                    total += count;
                                    output.write(data, 0, count);
                                }

                                output.flush();

                                output.close();
                                input.close();
                                // Toast.makeText(this, "Download complete.", Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), "Download complete",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO handle the error
                        error.printStackTrace();
                        CustomDialog.getInstance().dismiss();
                    }
                }, null);


        // request.setRetryPolicy(new DefaultRetryPolicy(60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext(), new HurlStack());
        mRequestQueue.add(request);


    }

    private void downloadfile(String file) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionClass.verifyPermission(grantResults)) {
            saveData();
        } else {
            ToastHelper.getInstance().showMessage("You can not download until you don't allow required permissions");
        }
    }
}
