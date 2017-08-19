package com.example.admin.ajs.api;


import com.example.admin.ajs.helper.PrefHelper;
import com.example.admin.ajs.utility.Utils;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${hitesh} on 12/8/2016.
 */

public class ResponseManager {

    public static <T> Object parseResponse(String mResponse, RequestCode mRequestCode, Gson mGson) {

        Object object = null;

        try {

            JSONObject jsonObject = new JSONObject(mResponse);

            switch (mRequestCode) {
                case clientInsert:

                    object = mGson.fromJson(jsonObject.getJSONObject("ClientInsert").toString(), mRequestCode.getLocalClass());

                    PrefHelper.getInstance().setString(PrefHelper.CLIENT_CREDENTIALS, Utils.objectToString((Serializable) object));
                    break;

                case GetUser:
                    object = mGson.fromJson(jsonObject.getJSONObject("GetUser").toString(), mRequestCode.getLocalClass());

                    PrefHelper.getInstance().setString(PrefHelper.CLIENT_CREDENTIALS, Utils.objectToString((Serializable) object));

                    break;

                case GetClientInfo:

                    object = mGson.fromJson(jsonObject.getJSONObject("GetClientInfo").toString(), mRequestCode.getLocalClass());
                    break;

                case ClientUpdate:

                    object = mGson.fromJson(jsonObject.getJSONObject("ClientUpdate").toString(), mRequestCode.getLocalClass());
                    PrefHelper.getInstance().setString(PrefHelper.CLIENT_CREDENTIALS, Utils.objectToString((Serializable) object));
                    break;

                case ClientChangePassword:
                    object = jsonObject.getJSONObject("ChatUserInsert").toString();
                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
