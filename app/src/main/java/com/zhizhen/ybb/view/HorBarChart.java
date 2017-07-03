package com.zhizhen.ybb.view;

import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by tc on 2017/6/27.
 */

public class HorBarChart {

    /**
     * @param barChart  控件
     * @param barData   数据
     * @param isSlither 用来控制是否可以滑动
     */
    public void showBarChart(BarChart barChart, BarData barData, boolean isSlither) {
//        //绘制高亮箭头
//        barChart.setDrawHighlightArrow(false);
        //设置值显示在柱状图的上边或者下边
        barChart.setDrawValueAboveBar(true);
        //设置绘制网格背景
        barChart.setDrawGridBackground(true);
        //设置双击缩放功能
        barChart.setDoubleTapToZoomEnabled(false);
        //设置规模Y启用
        barChart.setScaleYEnabled(false);
        //设置规模X启用
        barChart.setScaleXEnabled(false);
        //启用设置阻力
        barChart.setScaleEnabled(true);
        //设置每个拖动启用的高亮显示
        barChart.setHighlightPerDragEnabled(false);
        // 设置硬件加速功能
        barChart.setHardwareAccelerationEnabled(true);
        // 设置绘制标记视图
        barChart.setDrawMarkerViews(true);
        // 设置启用日志
        barChart.setLogEnabled(true);
        // 设置突出功能
//        barChart.setHighlightEnabled(true);
        // 设置拖动减速功能
        barChart.setDragDecelerationEnabled(true);
        // 数据描述
        barChart.getDescription().setEnabled(false);
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
//        barChart.setNoDataTextDescription("没有数据了");
        barChart.setNoDataText("O__O …");
        // 是否显示表格颜色
        barChart.setDrawGridBackground(false);


        /**
         * 下面这几个属性你们可以试试 挺有意思的
         * */
        // 设置是否可以触摸
        barChart.setTouchEnabled(isSlither);
        // 是否可以拖拽
        barChart.setDragEnabled(isSlither);//放大可拖拽
        // 是否可以缩放
        barChart.setScaleEnabled(false);
        // 集双指缩放
        barChart.setPinchZoom(false);


        // 设置背景
        barChart.setBackgroundColor(Color.parseColor("#01000000"));
        // 如果打开，背景矩形将出现在已经画好的绘图区域的后边。
        barChart.setDrawGridBackground(false);
        // 集拉杆阴影
        barChart.setDrawBarShadow(false);
        // 图例
        barChart.getLegend().setEnabled(false);
        // 设置数据
        barChart.setData(barData);
        // 隐藏右边的坐标轴 (就是右边的0 - 100 - 200 - 300 ... 和图表中横线)
        barChart.getAxisLeft().setEnabled(false);
        // 隐藏左边的左边轴 (同上)
        // 网格背景颜色
        barChart.setGridBackgroundColor(Color.parseColor("#00000000"));
        // 是否显示表格颜色
        barChart.setDrawGridBackground(false);
        // 设置边框颜色
        barChart.setBorderColor(Color.parseColor("#00000000"));
//        // 说明颜色
//        barChart.setDescriptionColor(Color.parseColor("#00000000"));
        // 拉杆阴影
        barChart.setDrawBarShadow(false);
        // 打开或关闭绘制的图表边框。（环绕图表的线）
        barChart.setDrawBorders(false);
        Legend mLegend = barChart.getLegend(); // 设置比例图标示
        // 设置窗体样式
        mLegend.setForm(Legend.LegendForm.CIRCLE);
        // 字体
        mLegend.setFormSize(4f);
        // 字体颜色
        mLegend.setTextColor(Color.parseColor("#00000000"));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(0xffffffff); // 设置轴标签的颜色。
        xAxis.setLabelCount((22 - 8) * 6);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String a = "";
                if (value % 6 == 0) {
                    if (value / 6 + 8 < 10) {
                        a = "0" + ((int) value / 6 + 8) + ":00";
                    } else {
                        a = "" + ((int) value / 6 + 8) + ":00";
                    }
                }
                return a;
            }
        });

        barChart.getAxisRight().setAxisMaximum(9f);
        barChart.getAxisRight().setAxisMinimum(-9f);
        barChart.getAxisRight().setDrawGridLines(true);
        barChart.getAxisRight().setDrawZeroLine(false);
        barChart.getAxisRight().setLabelCount(18, false);
        barChart.getAxisRight().setValueFormatter(new CustomFormatter());
        barChart.getAxisRight().setTextSize(9f);
        barChart.getAxisRight().setTextColor(0xffffffff);

        if (isSlither) {
            //当为true时,放大图
            // 为了使 柱状图成为可滑动的,将水平方向 放大 2.5倍
            barChart.invalidate();
            Matrix mMatrix = new Matrix();
            mMatrix.postScale(1f, 2f);
            barChart.getViewPortHandler().refresh(mMatrix, barChart, false);
            barChart.animateX(1000);
//            barChart.animateY(1000);
        } else {
            //当为false时 不对图进行操作
            barChart.animateY(1000);
        }
    }

    public static class CustomFormatter implements IValueFormatter, IAxisValueFormatter {

        private DecimalFormat mFormat;

        public CustomFormatter() {
            mFormat = new DecimalFormat("###");
        }

        // data
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(Math.abs(value)) + "°";
        }

        // YAxis
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format((Math.abs(value)) * 10) + "°";
        }
    }
}
