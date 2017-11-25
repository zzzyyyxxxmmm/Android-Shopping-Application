package com.example.wjk32.utils;

/**
 * Created by wjk32 on 11/17/2017.
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences.Editor;

public class ShareUtils {

    private static final String FIIL_NAME = "byhands";
    private static final String NODE_NAME = "welcome";
    private static final String USER_NAME = "username";

    // 获取boolean类型的值
    public static boolean getWelcomeBoolean(Context context){

        return context.getSharedPreferences(FIIL_NAME ,Context.MODE_PRIVATE).getBoolean(NODE_NAME, false);
    }

    // 写入Boolean类型的值
    public static void putWelcomeBoolean(Context context ,boolean isFirst){

        @SuppressLint("WrongConstant") Editor editor = context.getSharedPreferences(FIIL_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean(NODE_NAME, isFirst);
        editor.commit();
    }

    // 写入一个String类型的数据
    public static void putCityName(Context context ,String cityName){

        @SuppressLint("WrongConstant") Editor editor = context.getSharedPreferences(FIIL_NAME, Context.MODE_APPEND).edit();
        editor.putString("cityName", cityName);
        editor.commit();
    }

    // 获取String类型的值
    public static String getCityName(Context context){

        return context.getSharedPreferences(FIIL_NAME ,Context.MODE_PRIVATE).getString("cityName", "选择城市");
    }

    // 写入一个登录用户名
    public static void putUserName(Context context ,String cityName){

        @SuppressLint("WrongConstant") Editor editor = context.getSharedPreferences(USER_NAME, Context.MODE_APPEND).edit();
        editor.putString("userName", cityName);
        editor.commit();
    }

    // 获取登录用户名
    public static String getUserName(Context context){

        return context.getSharedPreferences(USER_NAME ,Context.MODE_PRIVATE).getString("userName", "用户登录");
    }


}