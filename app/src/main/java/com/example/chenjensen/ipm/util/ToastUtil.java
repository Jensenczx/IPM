package com.example.chenjensen.ipm.util;

import android.widget.Toast;

import com.example.chenjensen.ipm.base.MyApplication;

/**
 * Created by chenjensen on 16/3/7.
 */
public class ToastUtil {
    public static void showToast(int StringID){
        String text = MyApplication.getContext().getString(StringID);
        Toast.makeText(MyApplication.getContext(),text,Toast.LENGTH_SHORT).show();
    }
    
    public static void showToast(String text){
        Toast.makeText(MyApplication.getContext(),text,Toast.LENGTH_SHORT).show();
    }
}
