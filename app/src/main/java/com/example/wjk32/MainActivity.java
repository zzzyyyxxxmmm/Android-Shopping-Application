package com.example.wjk32;

import com.example.wjk32.Fragment.*;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import android.widget.RadioGroup.OnCheckedChangeListener;


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
                changeFragment(new FragmentGroupBuy(),true);
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
