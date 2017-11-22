package com.example.wjk32.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.wjk32.R;

/**
 * Created by wjk32 on 11/22/2017.
 */

public class SiderBar extends View {

    public SiderBar(Context context) {
        super(context);
    }

    public SiderBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint  paint = new Paint();//画笔
    // 26个字母
    public static String[] sideBar = { "HOT","A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    private OnTouchingLetterChangedListener letterChangedListener;
    private int choose;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(40);

        int height = getHeight();
        int width = getWidth();

        int each_height = height/sideBar.length;

        for (int i = 0; i < sideBar.length; i++) {

            float x =  width/2-paint.measureText(sideBar[i])/2;
            float y = (1+i)*each_height;

            canvas.drawText(sideBar[i], x, y, paint);
        }
    }
    public interface OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String s);
    }
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener){
        this.letterChangedListener = onTouchingLetterChangedListener;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final OnTouchingLetterChangedListener listener = letterChangedListener;
        final int c = (int)(y/getHeight()*sideBar.length);
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundResource(android.R.color.transparent);
                invalidate();
                break;

            default:
                setBackgroundResource(R.drawable.siderbar_background);
                if (c>0&&c<sideBar.length) {
                    if (listener!=null) {
                        listener.onTouchingLetterChanged(sideBar[c]);
                    }
                    choose = c;
                    invalidate();
                }
                break;
        }
        return true;

    }
}
