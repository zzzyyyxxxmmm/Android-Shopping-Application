package com.example.wjk32;

/**
 * Created by wjk32 on 11/24/2017.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wjk32.R;
import com.example.wjk32.consts.CONSTS;
import com.example.wjk32.entity.ResponseObject;
import com.example.wjk32.myutils.MyUtils;
import com.example.wjk32.utils.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.MobSDK;
import com.mob.commons.SHARESDK;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;


@ContentView(R.layout.my_login_act)

public class MyLoginActivity extends Activity implements PlatformActionListener{

    @ViewInject(R.id.login_back)
    private ImageView loginBack;

    @ViewInject(R.id.login_register)
    private TextView loginRegister;

    @ViewInject(R.id.login_uname)
    private EditText loginUname;

    @ViewInject(R.id.login_pass)
    private EditText loginPass;

    @ViewInject(R.id.login_check_num)
    private EditText loginCheckNum;

    @ViewInject(R.id.login_check_pass)
    private Button loginCheckPass;

    @ViewInject(R.id.login_btn)
    private Button loginButton;

    @ViewInject(R.id.login_by_weixin)
    private TextView loginByWeixin;

    @ViewInject(R.id.login_by_qq)
    private TextView loginByQQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        x.view().inject(this);
        setRandomView(loginCheckPass);
        //初始化shareSDK
        MobSDK.init(this);

        super.onCreate(savedInstanceState);
    }

    private void setRandomView(Button loginCheckPass) {

        loginCheckPass.setText(MyUtils.getRandom(4));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobSDK.clearUser();
    }

    @Event(value = {R.id.login_back, R.id.login_register, R.id.login_check_pass, R.id.login_btn, R.id.login_by_weixin, R.id.login_by_qq}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                //跳转到注册页面
                //startActivity(new Intent(this,MyRegisterActivity.class));
                finish();
                break;

            case R.id.login_check_pass:
                //更新验证码的值
                setRandomView(loginCheckPass);
                break;

            case R.id.login_btn:
                login();
                break;

            case R.id.login_by_weixin:
                loginByWeixin();
                break;

            case R.id.login_by_qq:
                loginByQQ();
                break;
            default:
                break;

        }

    }

    //微信第三方登录
    private void loginByWeixin() {
    }

    //QQ第三方登录
    private void loginByQQ() {
        //获取QQ登录平台

        Platform platform =ShareSDK.getPlatform(QQ.NAME);

        //增加监听事件授权
        platform.setPlatformActionListener(this);
        //判断授权已经验证
        if(platform.isClientValid()){
            String uName =platform.getDb().getUserName();
            //返回页面
            handleLogin(uName);
        }else {
            //如果没有授权登录
            platform.showUser(null);
        }
    }

    //普通登录
    private void login() {

        final String uName = loginUname.getText().toString();
        String pass = loginPass.getText().toString();
        String check_code_num = loginCheckNum.getText().toString();
        String check_code_random = loginCheckPass.getText().toString();

//        Log.e("check_code",check_code_num);
//        Log.e("check_code",check_code_random);

//        Log.e("check_code",uName);
//        Log.e("check_code",pass);

        RequestParams params = new RequestParams(CONSTS.USER_LOGIN);
        params.addQueryStringParameter("flag","login");
        params.addQueryStringParameter("username", uName);
        params.addQueryStringParameter("password", pass);

        //字符串比较用equals
        if (check_code_random.equals(check_code_num)) {
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    ResponseObject object = gson.fromJson(result, new TypeToken<ResponseObject>() {
                    }.getType());
                    //判断返回值得状态，状态为0，数据库未找到相应记录，登录失败。返回值为1，登录成功。
                    if (object.getState()==1) {
                        //页面跳转
                        handleLogin(uName);
                    } else {
                        Toast.makeText(getApplication(), "用户名或密码错误,请重新输入", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(getApplication(), "获取用户数据失败（输入用户名密码为空）,请重新输入", Toast.LENGTH_SHORT).show();
                    Log.e("login_status", ex.getMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Toast.makeText(getApplication(), "登录取消,请重新输入", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            Toast.makeText(this, "验证码输出错误，请重新输入", Toast.LENGTH_SHORT).show();
        }


    }

    private void handleLogin(String uName) {
        //uName = loginUname.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("loginName", uName);
        setResult(MyUtils.RequestLoginCode, intent);
        ShareUtils.putUserName(getApplication(),uName);
        finish();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //获取第三方平台显示的名称
        String uName = platform.getDb().getUserName();
        handleLogin(uName);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this,platform.getName()+"授权失败，请重试",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this,platform.getName()+"授权已取消",Toast.LENGTH_SHORT).show();
    }
}