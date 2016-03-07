package com.example.chenjensen.ipm.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.chenjensen.ipm.base.MyApplication;

/**
 * Created by chenjensen on 16/3/7.
 */
public class NetHelper {

    private static ConnectivityManager cm;

    public static boolean isNetConnected() {
        if(cm==null)
         cm = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==null&&cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()==null)
            return false;
        return true;
    }
}
