package com.example.wjk32;

/**
 * Created by wjk32 on 11/23/2017.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.wjk32.R;
import com.example.wjk32.consts.CONSTS;
import com.example.wjk32.entity.Goods;
import com.example.wjk32.entity.ResponseObject;
import com.example.wjk32.entity.Shop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


@ContentView(R.layout.search_map_act)
//呈现地图的样式
public class NearbyMapActivity extends Activity implements AMapLocationListener,LocationSource,AMap.OnMapLoadedListener,AMap.OnMarkerClickListener,AMap.OnInfoWindowClickListener,AMap.InfoWindowAdapter{

    MapView mMapView = null;

    @ViewInject(R.id.search_mymap)
    private MapView mapView;


//        implements LocationSource,
//		AMapLocationListener, OnMarkerClickListener, OnMapLoadedListener,
//		OnInfoWindowClickListener, InfoWindowAdapter {

    private AMap aMap;
    private double longitude = 116.367612;// 经度
    private double latitude = 40.075483;// 纬度
    private List<Goods> listDatas;// 商品信息

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    //	private LocationManagerProxy mAMapLocManager = null;
    private OnLocationChangedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        x.Ext.init(getApplication());

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setLocationSource(this);// 设置了定位的监听
//			aMap.getUiSettings().setMyLocationButtonEnabled(true); // 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);// 设置定位的类型为定位模式，参见类AMap。

            aMap.setOnMapLoadedListener(this);// 设置aMap加载成功事件的监听
            aMap.setOnMarkerClickListener(this);// 设置点击marker事件的监听器
            aMap.setInfoWindowAdapter(this);// 设置自定义的InfoWindow样式
            aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow的事件监听器
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                longitude = aMapLocation.getLongitude();
                latitude = aMapLocation.getLatitude();
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
        //导入数据
        loadData(String.valueOf(latitude), String.valueOf(longitude), "10000");
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;

        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();

        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Event(value = {R.id.search_back, R.id.search_refresh}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:// 返回按钮
                finish();
                break;
            case R.id.search_refresh:// 刷新加载数据
                loadData(String.valueOf(latitude), String.valueOf(longitude), "10000");

                break;

            default:
                break;
        }
    }

    // 按照定位的地址和搜索半径加载周边的数据
    private void loadData(String lat, String lon, String radius) {
        RequestParams params = new RequestParams(CONSTS.Goods_NearBy_URI);
        params.addQueryStringParameter("lat", lat);
        params.addQueryStringParameter("lon", lon);
        params.addQueryStringParameter("radius", radius);


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ResponseObject<List<Goods>> object = gson.fromJson(result, new TypeToken<ResponseObject<List<Goods>>>() {
                }.getType());
                listDatas = object.getDatas();

                // 设置地图的缩放
						/*
						 * new LatLng(latitude, longitude) 以当前位置为中心 16 缩放级别
						 * 地图缩放级别为4-20级
						 * 缩放级别较低时，您可以看到更多地区的地图；缩放级别高时，您可以查看地区更加详细的地图。 0
						 * 默认情况下，地图的方向为0度，屏幕正上方指向北方。当您逆时针旋转地图时，
						 * 地图正北方向与屏幕正上方的夹角度数为地图方向
						 * ，范围是从0度到360度。例如，一个90度的查询结果，在地图上的向上的方向指向正东 30
						 * 地图倾角范围为0-45度。
						 *
						 * 详情参考：http://lbs.amap.com/api/android-sdk/guide/camera/
						 */

                aMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(new CameraPosition(
                                new LatLng(latitude, longitude), 10, 0,
                                0)));

                addMarker(listDatas);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(NearbyMapActivity.this, "数据加载失败请重试",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    // 将数据标记到地图上
    public void addMarker(List<Goods> list) {
        // 声明标记对象
        MarkerOptions markerOptions;
        for (Goods goods : list) {
            Shop shop = goods.getShop();
            markerOptions = new MarkerOptions();
            // 设置当前的markerOptions对象的经纬度
            markerOptions.position(new LatLng(
                    Double.parseDouble(shop.getLat()), Double.parseDouble(shop
                    .getLon())));
            // 点击每一个图标显示信息 显示内容为商铺名称以及当前的商品价钱
            markerOptions.title(shop.getName()).snippet("￥" + goods.getPrice());
            // 不同类型的商品设置不同的类型图标
            if (goods.getCategoryId().equals("3")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_landmark_chi));
            } else if (goods.getCategoryId().equals("5")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_landmark_movie));
            } else if (goods.getCategoryId().equals("8")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_landmark_hotel));
            } else if (goods.getCategoryId().equals("6")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_landmark_life));
            } else if (goods.getCategoryId().equals("4")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_landmark_wan));
            } else {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_landmark_default));
            }
            // 在地图上显示所有的图标
            aMap.addMarker(markerOptions).setObject(goods);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    //根据商店的名称来获取当前的商品信息
    public Goods getGoodsByShopName(String shopName) {
        for (Goods goods : listDatas) {//遍历商品集合进行商铺的匹配
            if (goods.getShop().getName().equals(shopName)) {
                return goods;
            }
        }
        return null;
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    //	//当显示的窗体进行点击的时候
    @Override
    public void onInfoWindowClick(Marker marker) {
        //获取商店的名称
        String shopName = marker.getTitle();
        //根据商铺名称找到对应的商品
        Goods goods = getGoodsByShopName(shopName);
        if (goods != null) {
            //跳转到详情页面
            Intent intent = new Intent(this, GoodsDetailActivity.class);
            intent.putExtra("goods", goods);
            startActivity(intent);

        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}