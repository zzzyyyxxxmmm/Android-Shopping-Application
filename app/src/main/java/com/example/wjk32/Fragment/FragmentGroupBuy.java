package com.example.wjk32.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wjk32.GoodsDetailActivity;
import com.example.wjk32.R;
import com.example.wjk32.consts.CONSTS;
import com.example.wjk32.entity.Goods;
import com.example.wjk32.entity.ResponseObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.example.wjk32.consts.CONSTS.*;

/**
 * Created by wjk32 on 11/17/2017.
 */

public class FragmentGroupBuy extends Fragment {

    @ViewInject(R.id.index_listGoods)
    private PullToRefreshListView listGoods;
    @Event(value = R.id.index_listGoods,type = ListView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view,
                             int position, long id) {
        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("goods", listDatas.get(position-1));
        startActivity(intent);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_index_1,null);
        x.view().inject(this, view);
        x.Ext.init(getActivity().getApplication());


        listGoods.setMode(PullToRefreshBase.Mode.BOTH);
        listGoods.setScrollingWhileRefreshingEnabled(true);
        Log.i("TAG","gua");
        listGoods.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadDatas(listGoods.getScrollY()<0);

            }
        });

        new Handler(new  Handler.Callback() {

            @Override
            public boolean handleMessage(Message arg0) {
                listGoods.setRefreshing();
                return true;
            }
        }).sendEmptyMessageDelayed(0, 300);
        return view;
    }

    private int page, size = 5, count;
    private MyAdapter adapter;
    private List<Goods> listDatas;


    public void loadDatas(final boolean reflush) {
        if (reflush) {
            page = 0;
        }else{
            page++;
        }
        RequestParams params = new RequestParams(Goods_Datas_URL);
        params.addQueryStringParameter("page", page + "");
        params.addQueryStringParameter("size", size + "");


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listGoods.onRefreshComplete();
                Gson gson = new Gson();
                ResponseObject<List<Goods>> object = gson.fromJson(
                        result,
                        new TypeToken<ResponseObject<List<Goods>>>() {
                        }.getType());
                page = object.getPage();
                size = object.getSize();
                count = object.getCount();
                if (reflush) {
                    if(object.getSize()!=0) {
                        listDatas = object.getDatas();
                        adapter = new MyAdapter();
                        listGoods.setAdapter(adapter);
                    }
                }else{
                    if(object.getSize()==0){
                        listGoods.setMode(PullToRefreshBase.Mode.BOTH.PULL_FROM_START);
                        Toast.makeText(getActivity(),"no more products",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        listDatas.addAll(object.getDatas());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listGoods.onRefreshComplete();
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT)
                        .show();
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
            return listDatas.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int arg0, View convertView, ViewGroup arg2) {
            MyHolder holder = null;
            if (convertView == null) {
                holder = new MyHolder();
                convertView = LayoutInflater.from(arg2.getContext()).inflate(
                        R.layout.tuan_goods_list_item, null);
                x.view().inject(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }
            Goods goods = listDatas.get(arg0);
            Picasso.with(arg2.getContext()).load(goods.getImgUrl()).placeholder(R.drawable.ic_empty_dish).into(holder.image);


            StringBuffer sbf = new StringBuffer("￥"+goods.getValue());

            SpannableString spannable = new SpannableString(sbf);
            spannable.setSpan(new StrikethroughSpan(), 0, sbf.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.value.setText(spannable);
            holder.count.setText(goods.getBought() + " Sold out");
            holder.price.setText("$" + goods.getPrice());
            holder.title.setText(goods.getSortTitle());
            holder.titleContent.setText(goods.getTitle());
            return convertView;
        }

    }

    public class MyHolder {
        @ViewInject(R.id.index_gl_item_image)
        public ImageView image;
        @ViewInject(R.id.index_gl_item_title)
        public TextView title;
        @ViewInject(R.id.index_gl_item_titlecontent)
        public TextView titleContent;
        @ViewInject(R.id.index_gl_item_price)
        public TextView price;
        @ViewInject(R.id.index_gl_item_value)
        public TextView value;
        @ViewInject(R.id.index_gl_item_count)
        public TextView count;
    }
}
