package com.example.chenjensen.ipm.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.chenjensen.ipm.base.MyApplication;

/**
 * Created by chenjensen on 16/3/9.
 */
public class VolleyManager {
    public static RequestQueue mRequestQueue;
    public static VolleyManager mVolleyManager;
    private VolleyManager(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getContext());
    }

    public static VolleyManager getSingleInstance(){
        if(mVolleyManager==null){
            synchronized (VolleyManager.class){
                if(mVolleyManager==null)
                    mVolleyManager = new VolleyManager();
            }
        }
        return mVolleyManager;
    }

    public void addRequest(Request request){
        mRequestQueue.add(request);
    }
}
