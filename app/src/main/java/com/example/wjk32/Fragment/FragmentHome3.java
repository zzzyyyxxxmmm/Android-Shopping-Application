package com.example.wjk32.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wjk32.MyLoginActivity;
import com.example.wjk32.R;
import com.example.wjk32.myutils.MyUtils;
import com.example.wjk32.utils.ShareUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by wjk32 on 11/17/2017.
 */

@ContentView(R.layout.home_index_3)
public class FragmentHome3 extends Fragment {
    @ViewInject(R.id.my_index_login_image)
    private ImageView login_image;

    @ViewInject(R.id.my_index_login_text)
    private TextView login_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = x.view().inject(this,inflater,container);
        login_text.setText(ShareUtils.getUserName(getContext()));
        return view;
    }

    @Event(value = {R.id.my_index_login_image,R.id.my_index_login_text},type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.my_index_login_image:
            case R.id.my_index_login_text:
                login();
                break;

            default:
                break;
        }

    }
    //登录
    private void login() {
        Intent intent = new Intent(getActivity(), MyLoginActivity.class);
        startActivityForResult(intent, MyUtils.RequestLoginCode);
    }
    //登录页面回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==MyUtils.RequestLoginCode&& resultCode ==MyUtils.RequestLoginCode){
            login_text.setText(data.getStringExtra("loginName"));
            //头像设置
            login_image.setImageResource(R.drawable.protrait_def);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
