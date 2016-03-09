package com.example.chenjensen.ipm.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.chenjensen.ipm.base.MyApplication;

/**
 * Created by chenjensen on 16/2/25.
 */
public class SharedPreferenceHelper {

    private static final String SP_NAME = "ipm";

    public static boolean getBooleanValue(String KEY){
        SharedPreferences mSharedPreference = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return mSharedPreference.getBoolean(KEY,false);
    }

    public static void setBooleanValue(String KEY,boolean value){
        SharedPreferences mSharedPreference = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreference.edit();
        mEditor.putBoolean(KEY,value);
        mEditor.commit();
    }

    public static String getStringValue(String KEY){
        SharedPreferences mSharedPreference = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return mSharedPreference.getString(KEY,null);
    }

    public static void setStringValue(String KEY,String value){
        SharedPreferences mSharedPreference = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreference.edit();
        mEditor.putString(KEY,value);
        mEditor.commit();
    }

    public static int getIntValue(String KEY){
        SharedPreferences mSharedPreference = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return mSharedPreference.getInt(KEY,0);
    }

    public static void setIntValue(String KEY,int value){
        SharedPreferences mSharedPreference = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreference.edit();
        mEditor.putInt(KEY,value);
        mEditor.commit();
    }
}
