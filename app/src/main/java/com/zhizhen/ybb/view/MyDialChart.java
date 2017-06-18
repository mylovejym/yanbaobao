package com.zhizhen.ybb.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.bean.Dashboard;
import com.zhizhen.ybb.util.DensityUtil;
import com.zhizhen.ybb.view.renderer.PointerRender;

import java.util.LinkedHashMap;
import java.util.List;
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

    Paint textPaint;

    Map<Integer,Integer> maps = new LinkedHashMap();

    public MyDialChart(Context context, List<Dashboard> dashboard) {
        super(context);

        initPaint();
        putdata(dashboard);
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


//        maps.put(30,30);
//        maps.put(60,20);
//        maps.put(130,100);
//        maps.put(140,40);
//        maps.put(150,70);
        p = new Paint();
        p.setColor(0xff61cace);//0x61cace
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(0xff41adff);
        textPaint.setTextSize(50);
        textPaint.setAntiAlias(true);//去除锯齿
        textPaint.setFilterBitmap(true);//对位图进行滤波处理
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);




    }

    public void put(List<Dashboard> dashboard){
        putdata(dashboard);
        LogUtil.e("3333333333333333333333333");
//        postInvalidate();
        invalidate();

    }

    public void putdata(List<Dashboard> dashboard)
    {
        for(int i=0;i<dashboard.size();i++){
            float p = Float.valueOf(dashboard.get(i).getPercent());
            maps.put((i+1)*10, (int) p);
            LogUtil.e("4444444444444444");
//            if(i == dashboard.size()){
//                invalidate();
//                LogUtil.e("66666666666666");
//            }
        }
        LogUtil.e("555555555555555555");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmapbg,this.getLeft(),this.getTop(),null );
        canvas.drawBitmap(bitmapper,this.getLeft(),this.getTop()+bitmapbg.getHeight(),null );
        int needlew = bitmapneedle.getHeight()/2;
        Matrix matrix = new Matrix();
//        matrix.preTranslate(this.getLeft()+bitmapbg.getWidth()/2-bitmapneedle.getWidth(),this.getTop()+bitmapbg.getHeight()-bitmapneedle.getHeight()/2);
        matrix.setRotate(90,bitmapneedle.getWidth(),bitmapneedle.getHeight()/2);
//        matrix.setRotate(30);
        Bitmap bitmapDisplay = Bitmap.createBitmap(bitmapneedle,0,0,bitmapneedle.getWidth(),bitmapneedle.getHeight(),matrix,true);


        Path path=new Path();
        path.moveTo(bitmapbg.getWidth()/2, bitmapbg.getHeight());
        LogUtil.w((bitmapbg.getWidth()/2)+":"+bitmapbg.getHeight());
//        float i = 0.1f;
        for (Integer key : maps.keySet()) {
            LogUtil.e("*****************:"+key);
            LogUtil.e("*****************vvv:"+maps.get(key));
//            if(key>=0&&key<=180) {
                path.lineTo(bitmapbg.getWidth() / 2 - cos(key, maps.get(key)), bitmapbg.getHeight() - sin(key, maps.get(key)));
//            }
//            i+=0.1f;

        }
        //绘制一个贝塞尔曲线，第一个顶点是来控制曲线的弧度和方向，第二个顶点是弧线的结束顶点。

        path.close();
        canvas.drawPath(path,p);
        canvas.drawBitmap(bitmapDisplay,this.getLeft()+bitmapbg.getWidth()/2-bitmapDisplay.getWidth()+needlew,this.getTop()+bitmapbg.getHeight()-bitmapDisplay.getHeight(),null);
//        canvas.drawBitmap(bitmapneedle,matrix,null);
        canvas.drawBitmap(bitmapcircle,this.getLeft()+bitmapbg.getWidth()/2-bitmapcircle.getWidth()/2,this.getTop()+bitmapbg.getHeight()-bitmapcircle.getHeight()/2 ,null);
//        mPointer = new PointerRender();
//        mPointer.setStartXY(getCenterX(), getCenterY());
////        mPointer.setTotalAngle(mTotalAngle );
////        mPointer.setStartAngle(mStartAngle);
////        mPointer.setParentRadius(getRadius());
//        mPointer.render(canvas);
        float lift = this.getLeft()+bitmapbg.getWidth()/2-bitmapcircle.getWidth()/2;
        float top = this.getTop()+bitmapbg.getHeight()-bitmapcircle.getHeight()/2;
        float right = lift + bitmapcircle.getWidth();
        float bottom = top + bitmapcircle.getHeight();
//        Rect rect = new Rect((int)lift,(int)top,(int)right,(int)bottom);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top1 = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom1 = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (top+bitmapcircle.getHeight()/2 - top1/2 - bottom1/2);//基线中间点的y轴计算公式

        canvas.drawText("90°",lift+bitmapcircle.getWidth()/2+3,baseLineY,textPaint);

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
        int w = bitmapcircle.getWidth()- DensityUtil.dip2px(getContext(),4);
        return (int) (Math.sin(Math.PI*angle/180.0)*(v/100f*l+w/2));
    }
    private int cos(int angle, int v){
        int w = bitmapcircle.getWidth()- DensityUtil.dip2px(getContext(),4);
        return (int) (Math.cos(Math.PI*angle/180.0)*(v/100f*l+w/2));
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
