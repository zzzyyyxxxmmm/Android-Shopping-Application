package com.example.wjk32.entity;

/**
 * Created by wjk32 on 11/20/2017.
 */

public class ResponseObject<T> {
    private int state;
    private T datas;
    private int page;
    private int size;
    private int count;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public ResponseObject(){

    }
    public ResponseObject(int state,T datas){
        this.state=state;
        this.datas=datas;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public T getDatas() {
        return datas;
    }
    public void setDatas(T datas) {
        this.datas = datas;
    }



}