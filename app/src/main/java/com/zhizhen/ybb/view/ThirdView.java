package com.zhizhen.ybb.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by psylife00 on 2017/5/25.
 */

public class ThirdView extends DemoView {
    private String TAG = "BarChart10View";
    private BarChart chart = new BarChart();
    //轴数据源
    private List<String> chartLabels = new LinkedList<String>();
    private List<BarData> chartData = new LinkedList<BarData>();
    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();

    List <Integer> vals = new ArrayList<>();

    int start = 0;


    //CategoryAxisRender cateAxis =new CategoryAxisRender();


    public ThirdView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public ThirdView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public ThirdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        for(int i= 0;i<14*6;i++){
            vals.add(0);
        }
        chartLabels();
        chartDataSet();
        chartDesireLines();
        chartRender();

        //綁定手势滑动事件
        this.bindTouch(this,chart);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        if(null !=chart)chart.setChartRange(w,h);
    }


    private void chartRender()
    {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2] + 100.f, ltrb[3]);
            chart.getPlotArea().extWidth(DensityUtil.dip2px(getContext(),500));


            //DensityUtil.dip2px(this.getContext(), 20)

            //显示边框
//            chart.showRoundBorder();

//            //标题
//            chart.setTitle("US Population by Decade(millions)");
//            chart.addSubtitle("(XCL-Charts Demo)");
//            chart.setTitleAlign(XEnum.HorizontalAlign.LEFT);

            //数据源
            chart.setDataSource(chartData);
            chart.setCategories(chartLabels);
            chart.setCustomLines(mCustomLineDataset);


            //数据轴
            chart.getDataAxis().setAxisMax(10);
            chart.getDataAxis().setAxisMin(0);
            chart.getDataAxis().setAxisSteps(2);


//            chart.getDataAxis().hideAxisLine();
            chart.getDataAxis().hideTickMarks();
            chart.getPlotGrid().hideHorizontalLines();
            chart.getPlotGrid().hideVerticalLines();
            chart.getCategoryAxis().hideTickMarks();


            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(tmp).toString();
                    return (label);
                }

            });



            chart.getCategoryAxis().getTickLabelPaint().setTextSize(17);

            //设定格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }});

            //隐藏Key
            chart.getPlotLegend().hide();


            //提高性能
            chart.disableHighPrecision();

            //柱形和标签居中方式
            chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);
            chart.setDataAxisLocation(XEnum.AxisLocation.LEFT);

//            chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.EVEN_ODD);


            chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
//            chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.EVEN_ODD);
//            chart.getCategoryAxis().hideAxisLine();
//            chart.getDataAxis().setHorizontalTickAlign(Align.RIGHT);
//            chart.getDataAxis().setTickLabelMargin( DensityUtil.dip2px(this.getContext(), 30) );
            float margin = DensityUtil.dip2px(getContext(), 20);
            chart.setXTickMarksOffsetMargin(margin);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void chartDataSet()
    {
        //标签对应的柱形数据集
        List<Double> dataSeriesA= new LinkedList<Double>();
        //依数据值确定对应的柱形颜色.
        List<Integer> dataColorA= new LinkedList<Integer>();

        dataSeriesA.add((double) 5);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double)6);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 8);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        /////////
        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));
        dataSeriesA.add((double) 5);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double)6);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 8);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        /////////
        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));
        dataSeriesA.add((double) 5);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double)6);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 8);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        /////////
        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));
        dataSeriesA.add((double) 5);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double)6);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 8);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        /////////
        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));
        dataSeriesA.add((double) 5);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double)6);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 8);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        /////////
        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 7);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 3);
        dataColorA.add(Color.rgb(171, 42, 96));

        dataSeriesA.add((double) 2);
        dataColorA.add(Color.rgb(171, 42, 96));

        ///


        //此地的颜色为Key值颜色及柱形的默认颜色
        BarData BarDataA = new BarData("",dataSeriesA,dataColorA,
                Color.rgb(53, 169, 239));

        chartData.add(BarDataA);
    }

    private void chartLabels()
    {

        for(int i=2000;i<2010;i++)
        {
            chartLabels.add(Integer.toString(i));
        }
    }

    /**
     * 期望线/分界线
     */
    private void chartDesireLines()
    {
        CustomLineData s = new CustomLineData("",0d,Color.BLUE,3);
        s.setLineStyle(XEnum.LineStyle.DOT);
        mCustomLineDataset.add(s);

        mCustomLineDataset.add(new CustomLineData("",2d,Color.rgb(242, 242, 242),3));
        mCustomLineDataset.add(new CustomLineData("",4d,Color.rgb(242, 242, 242),6));
        mCustomLineDataset.add(new CustomLineData("",6d,Color.rgb(242, 242, 242),3));

        //mCustomLineDataset.add(new CustomLineData("200",200d,Color.BLACK,4));
        //mCustomLineDataset.add(new CustomLineData("250",250d,Color.RED,5));
        mCustomLineDataset.add(new CustomLineData("",8d,Color.rgb(242, 242, 242),5));

    }



    @Override
    public void render(Canvas canvas) {
        try{
            Log.w("",chart.getPlotArea().getLeft()+";"+chart.getPlotArea().getPlotRight());
            chart.render(canvas);

            /*
            float margin =  DrawHelper.getInstance().getPaintFontHeight(
            		chart.getCategoryAxis().getTickLabelPaint()
            ) + chart.getCategoryAxis().getTickLabelMargin() ;

            margin +=  DrawHelper.getInstance().getPaintFontHeight(
            		cateAxis.getTickLabelPaint()) / 3;


            cateAxis.renderAxis(canvas,
            		chart.getPlotArea().getLeft(),
            		chart.getPlotArea().getBottom() + margin ,
            		chart.getPlotArea().getRight(),
            		chart.getPlotArea().getBottom() + margin );

            cateAxis.setDataBuilding(chartLabels);
            */



        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            triggerClick(event.getX(),event.getY());
        }
        return true;
    }


    //触发监听
    private void triggerClick(float x,float y)
    {
        BarPosition record = chart.getPositionRecord(x,y);
        if( null == record) return;

        BarData bData = chartData.get(record.getDataID());
        Double bValue = bData.getDataSet().get(record.getDataChildID());

        Toast.makeText(this.getContext(),
                "info:" + record.getRectInfo() +
                        " Key:" + bData.getKey() +
                        " Current Value:" + Double.toString(bValue),
                Toast.LENGTH_SHORT).show();

        chart.showFocusRectF(record.getRectF());
        chart.getFocusPaint().setStyle(Style.STROKE);
        chart.getFocusPaint().setStrokeWidth(3);
        chart.getFocusPaint().setColor(Color.GREEN);
        this.invalidate();
    }
}
