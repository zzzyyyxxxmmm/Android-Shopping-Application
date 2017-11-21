package com.example.wjk32.consts;

/**
 * Created by wjk32 on 11/20/2017.
 */

//the used URL in the project
public class Consts {

    //dianping-server or dianping
    //make sure th
    public static final String HOST="http://192.168.0.2:8080/ls_server";

    //the city data URL of server
    public static final String CITY_DATA_URI=HOST+"/api/city";

    //the category data URL of server
    public static final String Sorts_DATA_URI=HOST+"/api/category";

    //the goods data URL of server
    public static final String Goods_DATA_URI=HOST+"/api/goods";

    //the url of nearby information sever
    public static final String NEARBY_DATA_URI=HOST+"/api/nearby";

    //the url of User information sever
    public static final String USER_DATA_URI=HOST+"/api/user?flag=login";

    //the url of User Register server
    public static final String USER_REGISTER_URI=HOST+"/api/user?flag=register";
}