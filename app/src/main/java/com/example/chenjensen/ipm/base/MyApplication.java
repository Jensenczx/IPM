package com.example.chenjensen.ipm.base;

import android.app.Application;
import android.content.Context;

import com.example.chenjensen.ipm.imageloader.ImageLoader;

/**
 * Created by chenjensen on 16/2/25.
 */
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initImageLoader();
    }

    public void initImageLoader(){
        ImageLoader loader = ImageLoader.getInstance();
    }

    public static Context getContext(){
        return context;
    }
}
