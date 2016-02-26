package com.example.chenjensen.ipm.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by chenjensen on 16/2/25.
 */
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
