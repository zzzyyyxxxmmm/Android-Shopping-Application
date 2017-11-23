package com.example.wjk32.myutils;

import com.example.wjk32.R;

/**
 * Created by wjk32 on 11/20/2017.
 */



public class MyUtils {
    public static final int REQUEST_CODE=2;
    public static final int REQUEST_CITY_CODE = 2;
    //result code
    public static final int RESULT_CODE=123;
    public static String[] navsSort= {"Foods","Movie","Hotel","KTV","buffet","Entertainment",
            "Traval","Shopping","Beauty","Child","Clothing","cosmetic","Sports","Service","All"};
    public static int[] navsSortImages = {R.drawable.icon_home_food_99,R.drawable.icon_home_movie_29,
            R.drawable.icon_home_hotel_300,R.drawable.icon_home_ktv_31,R.drawable.icon_home_self_189,
            R.drawable.icon_home_happy_2,R.drawable.icon_home_flight_400,R.drawable.icon_home_shopping_3,
            R.drawable.icon_home_liren_442,R.drawable.icon_home_child_13,R.drawable.icon_home_nvzhuang_84,
            R.drawable.icon_home_meizhuang_173,R.drawable.icon_home_yundong_20,R.drawable.icon_home_life_46,
            R.drawable.icon_home_all_0};
    public static String[] allCategray = {"All categories","New","Foods","Entertainment",
            "Movie","Service","Photos","Hotel","Traval","Beauty","Education","public good","Shopping"};
    public static int[] allCategrayImages = {R.drawable.ic_all,R.drawable.ic_newest,
            R.drawable.ic_food,R.drawable.ic_entertain,R.drawable.ic_movie,R.drawable.ic_life,
            R.drawable.ic_photo,R.drawable.ic_hotel,R.drawable.ic_travel,R.drawable.ic_beauty,
            R.drawable.ic_edu,R.drawable.ic_luck,R.drawable.ic_shopping};
    public static long allCategoryNumber[] = new long[allCategray.length+5];

    private static String RADOMS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz123456789";

    public static String getRandom(int num){
        StringBuffer stb = new StringBuffer();
        for(int i = 0;i<num;i++){

            int random =(int)(Math.random()*RADOMS.length());
            stb.append(RADOMS.charAt(random));
        }
        return stb.toString();
    }

}