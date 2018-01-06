package com.example.wjk32;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import cn.sharesdk.integhelper.Main;

/**
 * Created by wjk32 on 1/5/2018.
 */

@ContentView(R.layout.welcome)
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Log.i("TAG","gugaugau");
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                startActivity(new Intent(getApplicationContext(), WelcomeGuide.class));
                return false;
            }
        }).sendEmptyMessageDelayed(0,3000);
    }
}
