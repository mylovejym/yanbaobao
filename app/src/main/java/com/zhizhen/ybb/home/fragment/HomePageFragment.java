package com.zhizhen.ybb.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.base.YbBaseFragment;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.GetStatistics;
import com.zhizhen.ybb.bean.Histogram;
import com.zhizhen.ybb.home.contract.HomeContract;
import com.zhizhen.ybb.home.model.HomePageModel;
import com.zhizhen.ybb.home.presenter.HomePagePresenter;
import com.zhizhen.ybb.lanya.MyBLEActivity;
import com.zhizhen.ybb.loginpass.LoginActivity;
import com.zhizhen.ybb.view.BarCharts;
import com.zhizhen.ybb.view.MyDialChart;
import com.zhizhen.ybb.view.SecondChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * Created by psylife00 on 2016/12/20.
 */

public class HomePageFragment extends YbBaseFragment<HomePagePresenter, HomePageModel> implements HomeContract.HomePageView {
    @BindView(R.id.view_circle)
    View viewCircle;
    @BindView(R.id.my_dial_chart)
    MyDialChart myDialChart;
    @BindView(R.id.view_tday)
    View viewTday;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.text_b)
    TextView textB;
    @BindView(R.id.txt_health)
    TextView txtHealth;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.txt_teday)
    TextView txtTeday;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.SecondChart)
    SecondChart secondChart;
    @BindView(R.id.view_black)
    View viewBlack;
    @BindView(R.id.txt2)
    TextView txt2;
    @BindView(R.id.text_two)
    TextView textTwo;
    @BindView(R.id.view_error)
    View viewError;
    @BindView(R.id.btn_week)
    Button btnWeek;
    @BindView(R.id.btn_day)
    Button btnDay;
    Unbinder unbinder;
    @BindView(R.id.first_layout)
    LinearLayout firstLayout;
    @BindView(R.id.spreadBarChart)
    BarChart spreadBarChart;
    Unbinder unbinder1;

    private BarChart mBarChart;
    private BarCharts mBarCharts;

//    LoopRecyclerViewPager vpTop;

    private static HomePageFragment fragment = null;

    public static HomePageFragment getInstance() {
        if (fragment == null) {
            fragment = new HomePageFragment();
        }
        return fragment;
    }


    @Override
    public View getTitleView() {
        return new TitleBuilder(getActivity())
                .setRightText("蓝牙")
                .setTitleBgRes(R.color.blue_313245)
                .setRightOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), MyBLEActivity.class));
                })
                .build();
    }

    @Override
    protected void initLazyView() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.home_page_fragment;
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        mBarCharts = new BarCharts();
        mBarChart = (BarChart) view.findViewById(R.id.spreadBarChart);
        mBarCharts.showBarChart(mBarChart, getBarData((22 - 8) * 6, null,0), true);

    }

    static boolean onlyone;

    @Override
    public void initData() {
        super.initData();
//        if (!onlyone) {
//            mPresenter.static_data(YbBaseApplication.instance.getToken());
//            onlyone = true;
//        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            LogUtil.e("hepan", "显示封面");
            if(mPresenter!=null) {
                mPresenter.static_data(YbBaseApplication.instance.getToken());
                onlyone = true;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter!=null) {
            mPresenter.static_data(YbBaseApplication.instance.getToken());
            onlyone = true;
        }
    }


    @Override
    public void showError(Throwable e) {
//        onlyone = false;
    }

    @Override
    public void showData(BaseClassBean<GetStatistics> mPersonBean) {
        LogUtil.e("onlyone" + onlyone);
        if (mPersonBean.getStatus().equals("0")) {
            if (mPersonBean.getData().getDashboard() != null && onlyone) {
                LogUtil.e("1111111111111111111111111");
//                onlyone = false;
                textB.setText(""+((int)Double.parseDouble(mPersonBean.getData().getSit_info().get(0).getSit_time_percent()))+"%");
//                firstLayout.removeAllViews();
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                myDialChart = new MyDialChart(getActivity(), mPersonBean.getData().getDashboard());
//                firstLayout.addView(myDialChart,layoutParams);
//                firstLayout.invalidate();

                myDialChart.put(mPersonBean.getData().getDashboard());
//                myDialChart.invalidate();myDialChart.forceLayout();myDialChart.requestLayout();
//                myDialChart.invalidate();
                        secondChart.put(mPersonBean.getData().getDashboard());
                        mBarChart.setData(getBarData((22-8)*6,mPersonBean.getData().getHistogram(),0));
//                        mBarChart.invalidate();


            }

        } else if (onlyone) {
//            onlyone = false;
            if (mPersonBean.getStatus().equals("1009")) {
                Intent intent = new Intent(this.getContext(), LoginActivity.class);
                this.getContext().startActivity(intent);
                this.getActivity().finish();
            }
            Toast.makeText(this.getContext(), mPersonBean.getStatusInfo(), Toast.LENGTH_LONG).show();
        }
//        mBarChart.setData(getBarData((22-8)*6,null,1));
//        mBarChart.notifyDataSetChanged();
    }

    /**
     * 这个方法是初始化数据的
     *
     * @param count X 轴的个数
     */
    public BarData getBarData(int count, List<Histogram> histogram, int j) {
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
//            xValues.add("" + (i + 1) + "");// 设置每个柱壮图的文字描述
            String a = "";
            if (i % 6 == 0) {
                if (i / 6 + 8 < 10) {
                    a = "0" + ((int) i / 6 + 8) + ":00";
                } else {
                    a = ((int) i / 6 + 8) + ":00";
                }
            }
            xValues.add(a);
        }

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float value = 0;
            float value2 = 0;
            float value3 = 0;

            if (histogram != null && histogram.size() > i) {
                value = Float.parseFloat(histogram.get(i).getSerious_time()) ;
                value2 = Float.parseFloat(histogram.get(i).getMiddle_time());
                value3 = Float.parseFloat(histogram.get(i).getMild_time());
            }
            if(j!=0){
//                value = (float) (Math.random() * 10/*100以内的随机数*/);
                value = (float) (Math.random() * 10/*100以内的随机数*/);
            value2 = (float) (Math.random() * 10/*100以内的随机数*/);
            value3 = (float) (Math.random() * 10/*100以内的随机数*/);
            }
//            value = (float) (Math.random() * 10/*100以内的随机数*/);
//            value2 = (float) (Math.random() * 10/*100以内的随机数*/);
//            value3 = (float) (Math.random() * 10/*100以内的随机数*/);

            yValues.add(new BarEntry(new float[]{value, value2, value3}, i));

        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "测试图");
//        ArrayList<Integer> colors = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
////            colors.add(Color.parseColor("#75bfe2"));
//            colors.add(Color.parseColor("#41adff"));
//        }
        barDataSet.setColors(new int[]{Color.parseColor("#41adff"), Color.parseColor("#32CD99"), Color.parseColor("#00FFFF")});
        // 设置栏阴影颜色
        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        //可增加
        barDataSets.add(barDataSet);

        barDataSet.setValueTextColor(Color.parseColor("#ffffff"));
        // 绘制值
        barDataSet.setDrawValues(false);
        BarData barData = new BarData(xValues, barDataSets);
        return barData;
    }



}
