package com.example.chenjensen.ipm.util;

import android.util.DisplayMetrics;

import com.example.chenjensen.ipm.base.MyApplication;

/**
 * Created by chenjensen on 16/3/7.
 */
public class DisplayHelper {

    public static DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
    public static int w_screen = dm.widthPixels;
    public static int h_screen = dm.heightPixels;
}
