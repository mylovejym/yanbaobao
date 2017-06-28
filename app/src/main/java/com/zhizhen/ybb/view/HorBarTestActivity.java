package com.zhizhen.ybb.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.bean.Histogram;
import com.zhizhen.ybb.view.HorBarChart.CustomFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc on 2017/6/27.
 */

public class HorBarTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_biao);

        HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.horizontalBarChart);
        HorBarChart mBarCharts = new HorBarChart();
        mBarCharts.showBarChart(barChart, getBarData((22 - 8) * 6, null, 1), true);

    }


    /**
     * 这个方法是初始化数据的
     *
     * @param count X 轴的个数
     */
    public BarData getBarData(int count, List<Histogram> histogram, int j) {
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String a = "";
            if (i % 6 == 0) {
                if (i / 6 + 8 < 10) {
                    a = "0" + (i / 6 + 8) + ":00";
                } else {
                    a = (i / 6 + 8) + ":00";
                }
            }
            xValues.add(a);
        }

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float value = 0;
            float value2 = 0;

            if (histogram != null && histogram.size() > i) {
                value = -Float.parseFloat(histogram.get(i).getSerious_time_percent());
                value2 = Float.parseFloat(histogram.get(i).getMiddle_time_percent());
            }
            if (j != 0) {
                value = -(float) (Math.random() * 10/*10以内的随机数*/);
                value2 = (float) (Math.random() * 10/*10以内的随机数*/);
            }
            yValues.add(new BarEntry(i, new float[]{value, value2}));

        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "测试图");

        //设置条状图颜色
        barDataSet.setColors(new int[]{Color.parseColor("#7ddafb"), Color.parseColor("#4dcdfd")});

        // 设置栏阴影颜色
        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));

        // 绘制值
        barDataSet.setValueFormatter(new CustomFormatter());
        barDataSet.setValueTextSize(7f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        barDataSet.setValueTextColor(Color.parseColor("#ffffff"));
        barDataSet.setDrawValues(true);

        BarData barData = new BarData(barDataSet);

        return barData;
    }
}
