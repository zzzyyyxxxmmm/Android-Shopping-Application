package com.example.wjk32.consts;

/**
 * Created by wjk32 on 11/20/2017.
 */

//the used URL in the project
public class CONSTS {

    //dianping-server or dianping
    //make sure th
    public static final String HOST="http://192.168.0.2:8080/ls_server";

    //the city data URL of server
    public static final String CITY_DATA_URI=HOST+"/api/city";

    //商品分类
    public static final String Category_Data_URI = HOST+"/api/category";
    //商品的列表信息
    public static final String Goods_Datas_URL = HOST+"/api/goods";
    //附近商品列表信息
    public static final String Goods_NearBy_URI = HOST+"/api/nearby";
    //登录验证的URL
    public static final String USER_LOGIN = HOST+"/api/user";

}