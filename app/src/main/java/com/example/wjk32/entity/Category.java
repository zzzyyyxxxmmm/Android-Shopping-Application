package com.example.wjk32.entity;

/**
 * Created by wjk32 on 11/22/2017.
 */

public class Category {

    private String categoryId;
    private long categoryNumber;
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public long getCategoryNumber() {
        return categoryNumber;
    }
    public void setCategoryNumber(long categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    @Override
    public String toString() {
        return categoryId+" --> "+categoryNumber;
    }
}