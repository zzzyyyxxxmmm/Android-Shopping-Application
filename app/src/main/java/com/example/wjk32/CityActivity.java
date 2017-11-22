package com.example.wjk32;

/**
 * Created by wjk32 on 11/20/2017.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import com.example.wjk32.R;
import com.example.wjk32.view.SiderBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.wjk32.consts.Consts;
import com.example.wjk32.entity.City;
import com.example.wjk32.entity.ResponseObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CityActivity extends Activity implements SiderBar.OnTouchingLetterChangedListener {


    @ViewInject(R.id.city_list)
    private ListView listDatas;
    @ViewInject(R.id.index_city_back)
    private Button index_back;
    @ViewInject(R.id.index_city_flushcity)
    private Button index_flush;
    @ViewInject(R.id.city_sider_bar)
    private SiderBar siderBar;

    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_city_list);
        x.view().inject(this);
        View view=LayoutInflater.from(this).inflate(R.layout.home_city_search, null);
        listDatas.addHeaderView(view);
        new GetCityDataTask().execute();
        siderBar.setOnTouchingLetterChangedListener(this);
    }

    @Event({R.id.index_city_back,R.id.index_city_flushcity})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.index_city_back:
                finish();
                 break;
            case R.id.index_city_flushcity:
                new GetCityDataTask().execute();
                break;
            default:
                break;
        }
    }

    @Event(value=R.id.city_list,type=OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent,View view,int position,long id){
        Intent intent = new Intent();
        TextView textView=(TextView)view.findViewById(R.id.city_list_item_name);
        intent.putExtra("cityname",textView.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }


    public class GetCityDataTask extends AsyncTask<Void, Void, List<City>> {

        @Override
        protected List<City> doInBackground(Void... params) {

            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Consts.CITY_DATA_URI);
            try {
                HttpResponse response = client.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");
                    return ParseJsonResult(jsonResult);

                } else {
                    Log.i("wjk32", "response code：" + response.getStatusLine().getStatusCode());
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<City> cities) {
            super.onPostExecute(cities);
            cityList = cities;

            //Adapt show
            MyAdapter adapter = new MyAdapter(cityList);
            listDatas.setAdapter(adapter);
        }

        //解析Json格式的字符串
        private List<City> ParseJsonResult(String jsonResult) {
            //利用Gson解析Json字符串数据
            Gson gson = new Gson();
            ResponseObject<List<City>> responseObject = gson.fromJson(jsonResult,
                    new TypeToken<ResponseObject<List<City>>>() {
                    }.getType());
            return responseObject.getDatas();
        }

    }
        private StringBuffer buffer= new StringBuffer();
        private List<String> firstList= new ArrayList<String>();

        public class MyAdapter extends BaseAdapter{
            private List<City> listCityDatas;

            public MyAdapter(List<City> listCityDatas) {
                this.listCityDatas = listCityDatas;
            }

            @Override
            public int getCount() {
                return listCityDatas.size();
            }

            @Override
            public Object getItem(int i) {
                return listCityDatas.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Holder holder;
                if(view==null){
                    holder= new Holder();
                    view=LayoutInflater.from(CityActivity.this)
                            .inflate(R.layout.home_city_list_item, null);
                    x.view().inject(holder,view);
                    view.setTag(holder);
                }
                else{
                    holder=(Holder) view.getTag();
                }
                City city=cityList.get(i);
                String sortKey=city.getSortKey();
                String cityName=city.getName();
                //if the first char not exsit, make it visable
                if (buffer.indexOf(sortKey)==-1) {
                    buffer.append(sortKey);
                    firstList.add(cityName);

                }
                //如果是已经被添加到列表中的城市，说明是第一次首字母添加进去，则tvCitySortKey应该可见
                if (firstList.contains(cityName)) {
                    holder.keySort.setText(sortKey);
                    holder.keySort.setVisibility(View.VISIBLE);

                }
                //列表中不存在该城市的名字，说明首字母不是第一次出现，则tvCitySortKey不可见
                else{

                    holder.keySort.setVisibility(View.GONE);
                }
                holder.cityName.setText(cityName);
                return view;
            }

            public class Holder{
                @ViewInject(R.id.city_list_item_sort)
                public TextView keySort;

                @ViewInject(R.id.city_list_item_name)
                public TextView cityName;

            }
        }

    @Override

    public void onTouchingLetterChanged(String s) {
        listDatas.setSelection(findIndex(cityList, s));
    }
    public int findIndex(List<City> list, String s) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                City city = list.get(i);
                if (s.equals(city.getSortKey())) {
                    return i;
                }
            }
        } else {
            Toast.makeText(this, "no info", Toast.LENGTH_SHORT).show();
        }
        return -1;
    }
}