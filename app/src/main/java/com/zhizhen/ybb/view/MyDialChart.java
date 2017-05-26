package com.zhizhen.ybb.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.view.renderer.PointerRender;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by psylife00 on 2017/5/18.
 */

public class MyDialChart extends View{

    Bitmap bitmapbg;
    Bitmap bitmapneedle;
    Bitmap bitmapcircle;
    Bitmap bitmapper;

    PointerRender mPointer;

    int l;
    Paint p;

    Map<Integer,Integer> maps = new LinkedHashMap();

    public MyDialChart(Context context) {
        super(context);
        initPaint();
    }

    public MyDialChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyDialChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        bitmapbg = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.dial_bg);
        bitmapneedle = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.dial_needle);
        bitmapcircle = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.dial_circle);
        bitmapper = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.dial_per);
        l = bitmapbg.getWidth()/3-bitmapcircle.getWidth()/2;
        maps.put(0,0);maps.put(10,0);maps.put(20,0);maps.put(30,0);maps.put(40,0);maps.put(50,0);maps.put(60,0);maps.put(70,0);maps.put(80,0);maps.put(90,0);maps.put(100,0);
        maps.put(110,0);maps.put(120,0); maps.put(130,0);maps.put(140,0);maps.put(150,0);maps.put(160,0);maps.put(170,0);maps.put(180,0);


        maps.put(30,30);
        maps.put(60,20);
        maps.put(130,100);
        maps.put(140,40);
        maps.put(150,70);
        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmapbg,this.getLeft(),this.getTop(),null );
        canvas.drawBitmap(bitmapper,this.getLeft(),this.getTop()+bitmapbg.getHeight(),null );
        Matrix matrix = new Matrix();
//        matrix.preTranslate(this.getLeft()+bitmapbg.getWidth()/2-bitmapneedle.getWidth(),this.getTop()+bitmapbg.getHeight()-bitmapneedle.getHeight()/2);
        matrix.setRotate(30,bitmapneedle.getWidth(),bitmapneedle.getHeight()/2);
//        matrix.setRotate(30);
        Bitmap bitmapDisplay = Bitmap.createBitmap(bitmapneedle,0,0,bitmapneedle.getWidth(),bitmapneedle.getHeight(),matrix,true);


        Path path=new Path();
        path.moveTo(bitmapbg.getWidth()/2, bitmapbg.getHeight());
        LogUtil.w((bitmapbg.getWidth()/2)+":"+bitmapbg.getHeight());
//        float i = 0.1f;
        for (Integer key : maps.keySet()) {

            path.lineTo(bitmapbg.getWidth()/2-cos(key,maps.get(key)), bitmapbg.getHeight() -sin(key,maps.get(key)));
//            i+=0.1f;

        }
        //绘制一个贝塞尔曲线，第一个顶点是来控制曲线的弧度和方向，第二个顶点是弧线的结束顶点。

        path.close();
        canvas.drawPath(path,p);
        canvas.drawBitmap(bitmapDisplay,this.getLeft()+bitmapbg.getWidth()/2-bitmapDisplay.getWidth(),this.getTop()+bitmapbg.getHeight()-bitmapDisplay.getHeight(),null);
//        canvas.drawBitmap(bitmapneedle,matrix,null);
        canvas.drawBitmap(bitmapcircle,this.getLeft()+bitmapbg.getWidth()/2-bitmapcircle.getWidth()/2,this.getTop()+bitmapbg.getHeight()-bitmapcircle.getHeight()/2 ,null);
//        mPointer = new PointerRender();
//        mPointer.setStartXY(getCenterX(), getCenterY());
////        mPointer.setTotalAngle(mTotalAngle );
////        mPointer.setStartAngle(mStartAngle);
////        mPointer.setParentRadius(getRadius());
//        mPointer.render(canvas);
        super.onDraw(canvas);
    }

    /**
     * 得到中心点X坐标
     * @return X坐标
     */
    public float getCenterX() {
        return Math.abs(this.getLeft() + (this.getRight() - this.getLeft())/2);
    }

    private int sin(int angle, int v){
        LogUtil.w("aa:"+(v/100*l+bitmapcircle.getWidth()/2));
        return (int) (Math.sin(Math.PI*angle/180.0)*(v/100f*l+bitmapcircle.getWidth()/2));
    }
    private int cos(int angle, int v){
        return (int) (Math.cos(Math.PI*angle/180.0)*(v/100f*l+bitmapcircle.getWidth()/2));
    }

    /**
     * 得到中心点Y坐标
     * @return Y坐标
     */
    public float getCenterY() {
        return (Math.abs(this.getBottom() - (this.getBottom() - this.getTop())/2));
    }

    //默认执行，计算view的宽高,在onDraw()之前
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
//设置宽高
        setMeasuredDimension(width, height);
    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
//        if (specMode == MeasureSpec.AT_MOST){
//
//        }
//        //fill_parent或者精确值
//        else
            if (specMode == MeasureSpec.EXACTLY){

        }else{
            specSize = bitmapbg.getWidth();
        }
        Log.i("这个控件的宽度----------","specMode=" +specMode+ " specSize=" +specSize);
        return specSize;
    }
    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
//        if (specMode == MeasureSpec.AT_MOST){
//        }
//        //fill_parent或者精确值
//        else
        if (specMode == MeasureSpec.EXACTLY){
        }else{
            specSize = bitmapbg.getHeight()+bitmapcircle.getHeight()/2;
        }
        Log.i("这个控件的高度----------","specMode:" +specMode +" specSize:" +specSize);
        return specSize;
    }


}
