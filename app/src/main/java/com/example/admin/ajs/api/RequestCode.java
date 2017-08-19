package com.example.admin.ajs.api;


import com.example.admin.ajs.model.LoginUserModel;

/**
 * Created by ${hitesh} on 12/7/2016.
 */

public enum RequestCode {

    clientInsert(LoginUserModel.class),
    GetClientInfo(LoginUserModel.class),
    ClientUpdate(LoginUserModel.class),
    ClientChangePassword(null),
    TenderInsert(null),
    GetFileInfo(null),
    ForgotPassword(LoginUserModel.class),
    GetUser(LoginUserModel.class);


    Class mLocalClass;

    RequestCode(Class mLocalClass) {

        this.mLocalClass = mLocalClass;
    }

    public Class getLocalClass() {
        return mLocalClass;
    }

    public void setLocalClass(Class mLocalClass) {
        this.mLocalClass = mLocalClass;
    }
}
