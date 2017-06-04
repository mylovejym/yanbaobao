package com.zhizhen.ybb.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;

import org.xclcharts.chart.AreaChart;
import org.xclcharts.chart.AreaData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.info.AnchorDataPoint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by psylife00 on 2017/5/20.
 */

public class SecondChart extends DemoView{

    private String TAG = "AreaChart01View";

    private AreaChart chart = new AreaChart();
    //标签集合
    private LinkedList<String> mLabels = new LinkedList<String>();
    //数据集合
    private LinkedList<AreaData> mDataset = new LinkedList<AreaData>();

    private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);


    public SecondChart(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public SecondChart(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public SecondChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        chartLabels();
        chartDataSet();
        chartRender();
        drawTitle();

        //綁定手势滑动事件
        this.bindTouch(this,chart);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w,h);
    }

    private void chartRender()
    {
        try{
//            设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //轴数据源
            //标签轴
            chart.setCategories(mLabels);

            chart.getCategoryAxis().getAxisPaint().setColor(Color.WHITE);
            chart.getCategoryAxis().getTickLabelPaint().setColor(Color.WHITE);
            chart.getCategoryAxis().getTickMarksPaint().setColor(Color.WHITE);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(30f);
            chart.getCategoryAxis().setTickLabelMargin(10);

            //数据轴
            chart.setDataSource(mDataset);

            chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEELINE);

            //数据轴最大值
            chart.getDataAxis().setAxisMax(100);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(20);

            chart.getDataAxis().getTickLabelPaint().setColor(Color.WHITE);//0xff303048
            chart.getDataAxis().getTickLabelPaint().setTextSize(30f);
//            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
//
//                @Override
//                public String textFormatter(String value) {
//                    // TODO Auto-generated method stub
////                    Double tmp = Double.parseDouble(value);
////                    DecimalFormat df=new DecimalFormat("#0");
////                    String label = df.format(tmp).toString();
//                    return (value +"%");
//                }
//
//            });



            //网格
            chart.getPlotGrid().showHorizontalLines();
            chart.getPlotGrid().showVerticalLines();
            //把顶轴和右轴隐藏
            //chart.hideTopAxis();
            //chart.hideRightAxis();
            //把轴线和刻度线给隐藏起来
            chart.getDataAxis().hideAxisLine();
            chart.getDataAxis().hideTickMarks();
            chart.getCategoryAxis().hideAxisLine();
            chart.getCategoryAxis().hideTickMarks();
            chart.disablePanMode();
            chart.disableScale();

				/*
				//标题
				chart.setTitle("区域图(Area Chart)");
				chart.addSubtitle("(XCL-Charts Demo)");
				//轴标题
				chart.getAxisTitle().setLowerAxisTitle("(年份)");
				*/
            //透明度
            chart.setAreaAlpha(200);

				/*
				//显示图例
				chart.getPlotLegend().showLegend();

				//激活点击监听
				chart.ActiveListenItemClick();
				//为了让触发更灵敏，可以扩大5px的点击监听范围
				chart.extPointClickRange(5);
				*/
            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(tmp).toString();
                    return tmp==0?"":(label+"%");
                }

            });

            //设定交叉点标签显示格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }});

            //chart.disablePanMode();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    private void chartDataSet()
    {
//        //将标签与对应的数据集分别绑定
//        //标签对应的数据集
//        List<Double> dataSeries1= new LinkedList<Double>();
//        dataSeries1.add(0d); //55
//        dataSeries1.add((double)60);
//        dataSeries1.add((double)71);
//        dataSeries1.add((double)40);
//        dataSeries1.add((double)35);
//
//        List<Double> dataSeries2 = new LinkedList<Double>();
//        dataSeries2.add((double)10);
//        dataSeries2.add((double)22);
//        dataSeries2.add((double)30);
//        dataSeries2.add((double)30); //30
//        dataSeries2.add((double)0); //15

        List<Double> dataSeries3 = new LinkedList<Double>();
        dataSeries3.add((double)35);
        dataSeries3.add((double)45);
        dataSeries3.add((double)65);
        dataSeries3.add((double)75);
        dataSeries3.add((double)55);
        dataSeries3.add((double)35);
        dataSeries3.add((double)45);
        dataSeries3.add((double)65);
        dataSeries3.add((double)75);
        dataSeries3.add((double)55);
        dataSeries3.add((double)35);
        dataSeries3.add((double)45);
        dataSeries3.add((double)65);
        dataSeries3.add((double)75);
        dataSeries3.add((double)55);
        dataSeries3.add((double)35);
        dataSeries3.add((double)45);
        dataSeries3.add((double)65);
        dataSeries3.add((double)75);

//        //设置每条线各自的显示属性
//        //key,数据集,线颜色,区域颜色
//        AreaData line1 = new AreaData("小熊",dataSeries1,Color.BLUE,Color.YELLOW);
//        //不显示点
//        line1.setDotStyle(XEnum.DotStyle.HIDE);
//		/*
//		line1.setApplayGradient(true);
//		line1.setAreaBeginColor(Color.WHITE);
//		line1.setAreaEndColor(Color.YELLOW);
//		*/
//
//        AreaData line2 = new AreaData("小小熊",dataSeries2,
//                Color.rgb(79, 200, 100),Color.GREEN);
//        //设置线上每点对应标签的颜色
//        line2.getDotLabelPaint().setColor(Color.RED);
//        //设置点标签
//        line2.setLabelVisible(true);
//        line2.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.RECT);
//        line2.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.rgb(250, 194, 142));


        AreaData line3 = new AreaData(null,dataSeries3,
                0xff41adff,0x00ffffff,0x00ffffff);
        //设置线上每点对应标签的颜色
        //line3.getDotLabelPaint().setColor(Color.YELLOW);
//        line3.setLineStyle(XEnum.LineStyle.DOT);


//        mDataset.add(line1);
        mDataset.add(line3);
//        mDataset.add(line2);

    }

    private void chartLabels()
    {
        mLabels.add("0°");
        mLabels.add("");
        mLabels.add("20°");
        mLabels.add("");
        mLabels.add("40°");
        mLabels.add("");
        mLabels.add("60°");
        mLabels.add("");
        mLabels.add("80°");
        mLabels.add("");
        mLabels.add("100°");
        mLabels.add("");
        mLabels.add("120°");
        mLabels.add("");
        mLabels.add("140°");
        mLabels.add("");
        mLabels.add("160°");
        mLabels.add("");
        mLabels.add("180°");
    }

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        super.onTouchEvent(event);
//        if(event.getAction() == MotionEvent.ACTION_UP)
//        {
//            triggerClick(event.getX(),event.getY());
//        }
//        return true;
//    }


    //触发监听
    private void triggerClick(float x,float y)
    {
        PointPosition record = chart.getPositionRecord(x,y);
        if( null == record) return;

        AreaData lData = mDataset.get(record.getDataID());
        Double lValue = lData.getLinePoint().get(record.getDataChildID());

		/*
		Toast.makeText(this.getContext(),
				record.getPointInfo() +
				" Key:"+lData.getLineKey() +
				" Label:"+lData.getLabel() +
				" Current Value:"+Double.toString(lValue),
				Toast.LENGTH_SHORT).show();
		*/
        float r = record.getRadius();
        chart.showFocusPointF(record.getPosition(),r + r*0.5f);
        //chart.getFocusPaint().setStyle(Style.STROKE);
        chart.getFocusPaint().setStrokeWidth(3);
        chart.getFocusPaint().setColor(Color.RED);
        chart.getFocusPaint().setTextAlign(Align.CENTER);


        //在点击处显示tooltip
        mPaintTooltips.setColor(Color.YELLOW);
        chart.getToolTip().getBackgroundPaint().setColor(Color.GRAY);
        //chart.getToolTip().setCurrentXY(x,y);
        chart.getToolTip().setCurrentXY(record.getPosition().x,record.getPosition().y);
        chart.getToolTip().setStyle(XEnum.DyInfoStyle.CAPRECT);

        chart.getToolTip().addToolTip(" Key:"+lData.getLineKey(),mPaintTooltips);
        chart.getToolTip().addToolTip(" Label:"+lData.getLabel(),mPaintTooltips);
        chart.getToolTip().addToolTip(" Current Value:" +Double.toString(lValue),mPaintTooltips);
        chart.getToolTip().setAlign(Align.CENTER);

        this.invalidate();

    }




    private void drawTitle()
    {
//        //标题
//        chart.setTitle("区域图(Area Chart)");
//        chart.addSubtitle("(XCL-Charts Demo)");
//        //轴标题
//        chart.getAxisTitle().setLowerTitle("(年份)");

        //显示图例
        chart.getPlotLegend().show();

        //激活点击监听
        chart.ActiveListenItemClick();
        //为了让触发更灵敏，可以扩大5px的点击监听范围
        chart.extPointClickRange(5);
        chart.showClikedFocus();



        //批注
        List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();

        AnchorDataPoint an2 = new AnchorDataPoint(1,3,XEnum.AnchorStyle.CIRCLE);
        an2.setBgColor(Color.GRAY);
        an2.setAnchorStyle(XEnum.AnchorStyle.CIRCLE);


        AnchorDataPoint an3 = new AnchorDataPoint(1,4,XEnum.AnchorStyle.CIRCLE);
        an3.setBgColor(Color.RED);
        an3.setAnchorStyle(XEnum.AnchorStyle.RECT);
        an3.setAreaStyle(XEnum.DataAreaStyle.STROKE);

        //从点到底的标识线
        AnchorDataPoint an4 = new AnchorDataPoint(2,2,XEnum.AnchorStyle.TOBOTTOM);
        an4.setBgColor(Color.YELLOW);
        an4.setLineWidth(15);

        AnchorDataPoint an5 = new AnchorDataPoint(2,1,XEnum.AnchorStyle.TORIGHT);
        an5.setBgColor(Color.WHITE);
        an5.setLineWidth(15);

        //AnchorDataPoint an6 = new AnchorDataPoint(2,1,XEnum.AnchorStyle.HLINE);
        //an6.setBgColor(Color.WHITE);
        //an6.setLineWidth(15);

        mAnchorSet.add(an2);
        mAnchorSet.add(an3);
        mAnchorSet.add(an4);
        mAnchorSet.add(an5);
        //mAnchorSet.add(an6);
        chart.setAnchorDataPoint(mAnchorSet);


    }
}
