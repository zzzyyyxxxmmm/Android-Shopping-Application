package com.example.wjk32;

import com.example.wjk32.Fragment.*;
import com.example.wjk32.R;
import com.example.wjk32.consts.*;

import android.app.Application;
import android.support.v4.app.FragmentTransaction;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.io.IOException;


public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {
    @ViewInject(R.id.main_bottom_tabs)
    private RadioGroup group;
    @ViewInject(R.id.main_home)
    private RadioButton main_home;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        //initial FragmentManger
        fragmentManager=getSupportFragmentManager();

        //set default choose button
        main_home.setChecked(true);
        group.setOnCheckedChangeListener(this);

        //change different fragment
        changeFragment(new FragmentHome(),false);


    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.main_home:
                changeFragment(new FragmentHome(),true);
                break;
            case R.id.main_home1:
                changeFragment(new FragmentHome1(),true);
                break;
            case R.id.main_home2:
                changeFragment(new FragmentHome2(),true);
                break;
            case R.id.main_home3:
                changeFragment(new FragmentHome3(),true);
                break;
        }
    }

    public void changeFragment(Fragment fragment, boolean isInit){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        if(!isInit){
            transaction.addToBackStack(null);
        }
        transaction.commit();

    }
}
