package com.example.wjk32;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.integhelper.Main;

import static android.content.ContentValues.TAG;

/**
 * Created by wjk32 on 1/5/2018.
 */

@ContentView(R.layout.welcome_guide)
public class WelcomeGuide extends Activity {

    @ViewInject(R.id.welcome_pager)
    private ViewPager viewPager;

    @ViewInject(R.id.buttom_pager)
    private Button button;

    private List<View> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initViewPager();
    }

    private void initViewPager() {
        list=new ArrayList<View>();
        ImageView iv=new ImageView(this);
        iv.setImageResource(R.drawable.guide_01);
        list.add(iv);

        ImageView iv1=new ImageView(this);
        iv1.setImageResource(R.drawable.guide_02);
        list.add(iv1);

        ImageView iv2=new ImageView(this);
        iv.setImageResource(R.drawable.guide_03);
        list.add(iv2);


        Log.i(TAG,"msg");
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    button.setVisibility(View.VISIBLE);
                }else{
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event(value=R.id.buttom_pager)
    private void click(View v){
        startActivity(new Intent(getBaseContext(), MainActivity.class));

    }

    class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }



}
