package com.example.wjk32.Fragment;

import com.example.wjk32.utils.*;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wjk32.myapplication.R;
import com.example.wjk32.utils.ShareUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

/**
 * Created by wjk32 on 11/17/2017.
 */

public class FragmentHome extends Fragment implements LocationListener {
    @ViewInject(R.id.index_top_city)
    private TextView topcity;
    private String CityName;
    private LocationManager locationManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homeindex, null);
        x.view().inject(this, view);
        topcity.setText(ShareUtils.getCityName(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkGPSisopen();
    }

    private void checkGPSisopen() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isOpen) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }
        startLocation();
    }

    private void startLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
        Log.i("TAG","endstartLocation");
    }
    private Handler handler= new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what==1){
                topcity.setText(CityName);
            }


            return false;
        }
    });


    //get locaiton and find city
    public void updateWithNewLocation(Location location){
        double lat=0.0,lng=0.0;
        if(location!=null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.i("TAG","lat="+lat+"lng="+lng);
        }else{
            CityName="can't locate";
        }
        //it may return various cities
        List<Address> list=null;
        Geocoder ge=new Geocoder(getActivity());
        try {
            list=ge.getFromLocation(lat,lng,2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                Address ad=list.get(i);
                CityName=ad.getLocality();
            }
        }
        handler.sendEmptyMessage(1);

    }
    @Override
    public void onLocationChanged(Location location) {
        updateWithNewLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //save current city in order to the next use
        ShareUtils.putCityName(getActivity(),CityName);

        stopLocation();

    }
    private void stopLocation(){
        locationManager.removeUpdates(this);
    }
}
