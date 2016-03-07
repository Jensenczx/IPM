package com.example.chenjensen.ipm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import com.example.chenjensen.ipm.R;
import com.example.chenjensen.ipm.util.ToastUtil;

/**
 * Created by chenjensen on 16/3/7.
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        State wifiState = null;
        State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if(wifiState !=null && State.CONNECTED==wifiState){
            ToastUtil.showToast(R.string.toast_wifi_connection);
        }
        else if( mobileState != null && State.CONNECTED != mobileState) {
            ToastUtil.showToast(R.string.toast_mobile_connection);
        } else if (wifiState == null && mobileState==null) {
            ToastUtil.showToast(R.string.toast_no_connection);
        }

    }
}
