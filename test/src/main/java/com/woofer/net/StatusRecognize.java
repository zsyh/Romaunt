package com.woofer.net;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/26.
 */
public class StatusRecognize {
    public static boolean getStatus(String s)
    {

        boolean status=true;

        try {
            JSONObject baseResponse = new JSONObject(s);
            status=Boolean.parseBoolean(baseResponse.getString("status"));
        }
        catch (JSONException e)
        {
            Log.e("NetWorkTest","JSONException: " + e);
        }

        return status;

    }
}
