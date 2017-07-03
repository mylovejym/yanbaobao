package com.zhizhen.ybb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.HorizontalBarChart;

/**
 * Created by psylife00 on 2017/6/29.
 */

public class MyHorizontalBarChart extends HorizontalBarChart {
    public MyHorizontalBarChart(Context context) {
        super(context);
    }

    public MyHorizontalBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    float mDownX;
    float mDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = event.getX();
                    mDownY = event.getY();
                    //ACTION_DOWN的时候，赶紧把事件hold住
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:

                    if(Math.abs(event.getX() - mDownX)<Math.abs(event.getY()-mDownY)) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else {
                        //发现不是自己处理，还给父类
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    //其实这里是多余的
                    getParent().requestDisallowInterceptTouchEvent(false);
            }


        return super.onInterceptTouchEvent(event);
    }
}
