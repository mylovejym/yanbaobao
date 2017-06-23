package com.zhizhen.ybb.view;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by psylife00 on 2017/6/22.
 */

public class LineCharts {

    public void showLineChart(LineChart chat, LineData data){

        chat.getDescription().setEnabled(false);
        chat.setBackgroundColor(Color.WHITE);
        chat.setDrawGridBackground(false);
//        chat.setDrawBarShadow(false);
//        chat.setHighlightFullBarEnabled(false);

        // 设置是否可以触摸
        chat.setTouchEnabled(false);
        // 是否可以拖拽
        chat.setDragEnabled(false);//放大可拖拽
        // 是否可以缩放
        chat.setScaleEnabled(false);
        // 集双指缩放
        chat.setPinchZoom(false);
        chat.setBackgroundColor(Color.TRANSPARENT);

        chat.setData(data);

        Legend l = chat.getLegend();
//        l.setWordWrapEnabled(true);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
        l.setEnabled(false);

        YAxis rightAxis = chat.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(100f);
        rightAxis.setLabelCount(0, true);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setEnabled(false);

        YAxis leftAxis = chat.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(100f);
        leftAxis.setLabelCount(6, true);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value==0?"":(((int)value)+"%");
            }
        });

//        leftAxis.setDrawAxisLine(false);
//        leftAxis.setEnabled(false);

        XAxis xAxis = chat.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setAxisMaximum(90f);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setLabelCount(9);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String a = "";
                if (value% 10 == 0) {

                    a = ((int)value*2)+"°";

                }
                return a;
            }
        });



    }

}
