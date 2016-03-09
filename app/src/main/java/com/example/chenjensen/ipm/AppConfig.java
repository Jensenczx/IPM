package com.example.chenjensen.ipm;

import com.example.chenjensen.ipm.base.MyApplication;
import com.example.chenjensen.ipm.entity.UserEntity;
import com.example.chenjensen.ipm.net.NetHelper;
import com.example.chenjensen.ipm.util.SharedPreferenceHelper;

/**
 * Created by chenjensen on 16/3/9.
 */
public class AppConfig {

    public static final String [] COLUMN_ARRAY = {"产品设计","交互体验","产品运营","原型设计"
            ,"业界动态","分析评测","职场攻略"};

    private static final String IS_FIRST_OPEN_KEY = "isfirstopen";
    private static final String IS_LOGIN = "islogin";
    private static final String USER_NAME = "username";
    private static final String USER_INTROUCE = "userintrouce";
    private static final String USER_PHOTO = "userphoto";


    public static boolean isFirstOpen(){
        return SharedPreferenceHelper.getBooleanValue(IS_FIRST_OPEN_KEY);
    }

    public static boolean isLogin(){
        return SharedPreferenceHelper.getBooleanValue(IS_LOGIN);
    }

    public static void setIsLogin(String key,boolean value){
        SharedPreferenceHelper.setBooleanValue(key,value);
    }

    public static int isFollow(String key){
        return SharedPreferenceHelper.getIntValue(key);
    }

    public static void setIsFollow(String key,int value){
        SharedPreferenceHelper.setIntValue(key,value);
    }

    public static UserEntity getUserInfo(){
        if (!isLogin()){
            UserEntity userEntity = new UserEntity();
            userEntity.setIntroduce(SharedPreferenceHelper.getStringValue(USER_INTROUCE));
            userEntity.setName(SharedPreferenceHelper.getStringValue(USER_NAME));
            userEntity.setPhoto(SharedPreferenceHelper.getStringValue(USER_PHOTO));
            return userEntity;
        }
        return null;
    }

    public void 

    public static void saveUserInfo(UserEntity entity){
        if(entity!=null){
            SharedPreferenceHelper.setStringValue(USER_NAME, entity.getName());
            SharedPreferenceHelper.setStringValue(USER_INTROUCE, entity.getIntroduce());
            SharedPreferenceHelper.setStringValue(USER_PHOTO, entity.getPhoto());
        }
    }

    public static boolean isNetworkAvailable(){
        return NetHelper.isNetworkConnected(MyApplication.getContext());
    }

}
