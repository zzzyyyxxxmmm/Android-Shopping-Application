package com.example.wjk32;

/**
 * Created by wjk32 on 11/22/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wjk32.R;
import com.example.wjk32.consts.CONSTS;
import com.example.wjk32.entity.Category;
import com.example.wjk32.entity.ResponseObject;
import com.example.wjk32.myutils.MyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.home_index_nav_all)
public class AllCategoryActivity extends Activity {
    @ViewInject(R.id.home_nav_all_categray)
    private ListView categoryList;

    private List<Category> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        x.Ext.init(getApplication());
        LoadCategroyData();
    }

    private void LoadCategroyData() {

        RequestParams params = new RequestParams(CONSTS.Category_Data_URI);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ResponseObject<List<Category>> object = gson.fromJson(result, new TypeToken<ResponseObject<List<Category>>>(){}.getType());
                category = object.getDatas();
                for(Category categoryitem:category){
                    int position = Integer.parseInt(categoryitem.getCategoryId());
                    MyUtils.allCategoryNumber[position-1] = categoryitem.getCategoryNumber();
                }

                MyAdapter adapter = new MyAdapter();
                categoryList.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplication(), "onErrorMsg"+ex.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return MyUtils.allCategray.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder myHolder = null;
            if (convertView == null) {
                myHolder = new MyHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.home_index_nav_all_item, null);
                x.view().inject(myHolder, convertView);
                convertView.setTag(myHolder);
            } else {
                myHolder = (MyHolder) convertView.getTag();
            }
            myHolder.textDesc.setText(MyUtils.allCategray[position]);
            myHolder.imageView
                    .setImageResource(MyUtils.allCategrayImages[position]);
            myHolder.textNumber.setText(MyUtils.allCategoryNumber[position]+"");
            return convertView;
        }
    }

    public class MyHolder {
        @ViewInject(R.id.home_nav_all_item_number)
        private TextView textNumber;
        @ViewInject(R.id.home_nav_all_item_desc)
        private TextView textDesc;
        @ViewInject(R.id.home_nav_all_item_image)
        private ImageView imageView;
    }

    @Event(value = R.id.home_nav_all_back,type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_nav_all_back:
                finish();
                break;

            default:
                break;
        }
    }
}