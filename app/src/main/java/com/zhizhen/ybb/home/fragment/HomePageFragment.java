package com.zhizhen.ybb.home.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blog.www.guideview.Component;
import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.base.YbBaseFragment;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.Dashboard;
import com.zhizhen.ybb.bean.GetStaticLateral;
import com.zhizhen.ybb.bean.GetStatistics;
import com.zhizhen.ybb.bean.Histogram;
import com.zhizhen.ybb.bean.StaticLateral;
import com.zhizhen.ybb.component.SimpleComponent;
import com.zhizhen.ybb.home.contract.HomeContract;
import com.zhizhen.ybb.home.model.HomePageModel;
import com.zhizhen.ybb.home.presenter.HomePagePresenter;
import com.zhizhen.ybb.lanya.MyBLEActivity;
import com.zhizhen.ybb.lanya.UartService;
import com.zhizhen.ybb.loginpass.LoginActivity;
import com.zhizhen.ybb.util.BLEUtils;
import com.zhizhen.ybb.util.SpUtils;
import com.zhizhen.ybb.view.BarCharts;
import com.zhizhen.ybb.view.HorBarChart;
import com.zhizhen.ybb.view.LineCharts;
import com.zhizhen.ybb.view.MyDialChart;
import com.zhizhen.ybb.view.SecondChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * Created by psylife00 on 2016/12/20.
 */

public class HomePageFragment extends YbBaseFragment<HomePagePresenter, HomePageModel> implements HomeContract.HomePageView {
    @BindView(R.id.view_circle)
    View viewCircle;
    //    @BindView(R.id.my_dial_chart)
    MyDialChart myDialChart;
    @BindView(R.id.view_tday)
    View viewTday;
    @BindView(R.id.txt)
    TextView txt;
    //    @BindView(R.id.text_b)
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
    @BindView(R.id.rl_guide_01)
    RelativeLayout rlGuide_01;
    Unbinder unbinder1;

    private BarChart mBarChart;
    private BarCharts mBarCharts;

    private LineChart mLineChart;
    private LineCharts mLineCharts;
    private ProgressBar progressbar;
    private TextView conn_text;

    private UartService mService = null;

    HorizontalBarChart horbarChart;
    HorBarChart mHorBarCharts;

//    LoopRecyclerViewPager vpTop;

    //功能引导
    private Guide guide;

    private static HomePageFragment fragment = null;

    public static HomePageFragment getInstance() {
        if (fragment == null) {
            fragment = new HomePageFragment();
        }
        return fragment;
    }

    TitleBuilder titleBuilder;


    @Override
    public View getTitleView() {
        titleBuilder = new TitleBuilder(getActivity())
                .setTitleText("健康报告")
                .setTitleTextColor(getActivity(), R.color.white)
                .setRightText("蓝牙")
                .setTitleBgRes(R.color.blue_313245)
                .setRightOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), MyBLEActivity.class));
                });
        return titleBuilder.build();
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
        getActivity().getWindow()
                .getDecorView()
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            getActivity().getWindow()
                                    .getDecorView()
                                    .getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            getActivity().getWindow()
                                    .getDecorView()
                                    .getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                        showGuideView();
                    }
                });
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        conn_text = (TextView) view.findViewById(R.id.conn_text);
        textB = (TextView) view.findViewById(R.id.text_b);
        myDialChart = (MyDialChart) view.findViewById(R.id.my_dial_chart);

        mBarCharts = new BarCharts();
        mBarChart = (BarChart) view.findViewById(R.id.spreadBarChart);
        mBarCharts.showBarChart(mBarChart, getBarData((22 - 8) * 6, null, 0), true);

        mLineCharts = new LineCharts();
        mLineChart = (LineChart) view.findViewById(R.id.lineChart);
        mLineCharts.showLineChart(mLineChart, getLinrData(null));

        mHorBarCharts = new HorBarChart();
        horbarChart = (HorizontalBarChart) view.findViewById(R.id.horizontalBarChart);
        mHorBarCharts.showBarChart(horbarChart, getHorBarData((22 - 8) * 6, null, 0), true);
        service_init();
    }


    private void service_init() {
        Intent bindIntent = new Intent(getActivity(), UartService.class);
        getActivity().bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        intentFilter.addAction(UartService.ACTION_UP_DATA);
        return intentFilter;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            Log.d(TAG, "onServiceConnected mService= " + mService);
            mService.initialize();

            if (mService.isBleConnect()) {
                conn_text.setVisibility(View.GONE);
                conn_text.setText("已连接");
            } else {
                conn_text.setVisibility(View.VISIBLE);
                conn_text.setText("蓝牙未连接，请您开启蓝牙并连接坐姿检测仪");
                LogUtil.e("aaaaaaa:");
                if (SpUtils.getBindBLEDevice(getActivity()) != null && SpUtils.getBoolean(getContext(), "isbinded", false)) {
                    LogUtil.e("aaaaaaa:连接");
                    mService.connect(SpUtils.getBindBLEDevice(getActivity()).getAddress());
                }
            }

        }

        public void onServiceDisconnected(ComponentName classname) {
            ////     mService.disconnect(mDevice);
            mService = null;
        }
    };

    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            LogUtil.e("actionactionactionactionaction:" + action);

            final Intent mIntent = intent;
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                conn_text.setVisibility(View.GONE);
                conn_text.setText("已连接");
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                conn_text.setVisibility(View.VISIBLE);
                conn_text.setText("蓝牙未连接，请您开启蓝牙并连接坐姿检测仪");
            }


            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {

            }
            if (action.equals(UartService.ACTION_UP_DATA)) {
                LogUtil.e("ACTION_UP_DATA0:" + UartService.ACTION_UP_DATA);
                if (mPresenter != null) {
                    LogUtil.e("ACTION_UP_DATA1:" + UartService.ACTION_UP_DATA);
                    mPresenter.static_data(YbBaseApplication.instance.getToken());
                    mPresenter.static_lateral(YbBaseApplication.instance.getToken());
                    onlyone = true;
                }
            }
            //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {

                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);

                try {
//                            String text = new String(txValue, "UTF-8");
                    String text = BLEUtils.bytesToHexString(txValue);
//                            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    LogUtil.e("RX:" + text);
//                            listAdapter.add("["+currentDateTimeString+"] RX: "+text);
//                            messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                    if (text.equalsIgnoreCase("AA0155") || text == null) {
                        progressbar.setVisibility(View.GONE);
                        isfirst = false;
                    } else if (text.startsWith("aa0a03")) {
                        String str = text.substring(6, 14);
                        Long longStr = Long.parseLong(str, 16) + 946656000;
                        if (!isfirst) {
                            firstTime = longStr;
                            progressbar.setVisibility(View.VISIBLE);
                            progressbar.setMax((int) (Calendar.getInstance().getTimeInMillis() / 1000 - firstTime));
                            isfirst = true;
                        }
                        progressbar.setProgress((int) (longStr - firstTime));
                    }

                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }


            }
            //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)) {
//                showMessage("Device doesn't support UART. Disconnecting");

            }


        }
    };
    long firstTime;
    boolean isfirst;

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
            if (mPresenter != null) {
                mPresenter.static_data(YbBaseApplication.instance.getToken());
                mPresenter.static_lateral(YbBaseApplication.instance.getToken());
                onlyone = true;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.static_data(YbBaseApplication.instance.getToken());
            mPresenter.static_lateral(YbBaseApplication.instance.getToken());
            onlyone = true;
        }
        if (mService != null && !mService.isBleConnect() && SpUtils.getBindBLEDevice(getActivity()) != null && SpUtils.getBoolean(getContext(), "isbinded", false)) {
            mService.connect(SpUtils.getBindBLEDevice(getActivity()).getAddress());
        }
    }


    @Override
    public void showError(Throwable e) {
//        onlyone = false;
    }

    /**
     * 这个方法是初始化数据的
     *
     * @param count X 轴的个数
     */
    public BarData getHorBarData(int count, List<StaticLateral> static_lateral, int j) {
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

            if (static_lateral != null && static_lateral.size() > i) {
                if ((90 - static_lateral.get(i).getAverageforFloat()) > 0) {
                    value = -(90 - static_lateral.get(i).getAverageforFloat()) / 10f;
                } else {
                    value2 = -(90 - static_lateral.get(i).getAverageforFloat()) / 10f;
                }
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
        barDataSet.setDrawValues(false);
//        barDataSet.setValueFormatter(new CustomFormatter());
//        barDataSet.setValueTextSize(7f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
//        barDataSet.setValueTextColor(Color.parseColor("#ffffff"));
//        barDataSet.setDrawValues(true);

        BarData barData = new BarData(barDataSet);

        return barData;
    }

    @Override
    public void showData(BaseClassBean<GetStatistics> mPersonBean) {
        LogUtil.e("onlyone" + onlyone);
        if (mPersonBean.getStatus().equals("0")) {
            if (mPersonBean.getData().getDashboard() != null && onlyone) {
                LogUtil.e("1111111111111111111111111");
//                int h= mPersonBean.getData().getHistogram().size()/6+8;
//                int m = mPersonBean.getData().getHistogram().size()%6*10;
//                String time= "";
//                if (h< 10) {
//                    time = "0" + h + ":";
//                } else {
//                    time = ""+ h + ":";
//                }
//                if(m ==0){
//                    time += "00";
//                }else{
//                    time += m;
//                }
//
//                titleBuilder.setSubtitle(mPersonBean.getData().getHistogram().get(0).getMeasureDate()+" "+time);
//                onlyone = false;
                textB.setText("" + ((int) Double.parseDouble(mPersonBean.getData().getSit_info().get(0).getSit_time_percent())) + "%");
                LogUtil.e("222222222222222222");
//                firstLayout.removeAllViews();
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                myDialChart = new MyDialChart(getActivity(), mPersonBean.getData().getDashboard());
//                firstLayout.addView(myDialChart,layoutParams);
//                firstLayout.invalidate();

                myDialChart.put(mPersonBean.getData().getDashboard());
                LogUtil.e("333333333333333333333");
//                myDialChart.invalidate();myDialChart.forceLayout();myDialChart.requestLayout();
//                myDialChart.invalidate();
                mLineCharts.showLineChart(mLineChart, getLinrData(mPersonBean.getData().getDashboard()));
//                        secondChart.put(mPersonBean.getData().getDashboard());
                mBarChart.setData(getBarData((22 - 8) * 6, mPersonBean.getData().getHistogram(), 0));
//                        mBarChart.invalidate();
//                mHorBarCharts.showBarChart(horbarChart, getHorBarData((22 - 8) * 6, mPersonBean.getData().getStatic_lateral(), 0), true);


            }

        } else if (onlyone) {
//            onlyone = false;
            if (mPersonBean.getStatus().equals("1009")) {
                Intent intent = new Intent(this.getContext(), LoginActivity.class);
                this.getContext().startActivity(intent);
                this.getActivity().finish();
            }
            if (mPersonBean.getStatus().equals("1137")) {
                textB.setText("0%");
                titleBuilder.setSubtitle("");
                myDialChart.clear();
                mLineCharts.showLineChart(mLineChart, getLinrData(null));
                mBarCharts.showBarChart(mBarChart, getBarData((22 - 8) * 6, null, 0), true);
                mHorBarCharts.showBarChart(horbarChart, getHorBarData((22 - 8) * 6, null, 0), true);
            }
            Toast.makeText(this.getContext(), mPersonBean.getStatusInfo(), Toast.LENGTH_LONG).show();
        }
//        mBarChart.setData(getBarData((22-8)*6,null,1));
//        mBarChart.notifyDataSetChanged();
    }

    @Override
    public void showDatalateral(BaseClassBean<GetStaticLateral> mPersonBean) {
        if (mPersonBean.getStatus().equals("0")) {

            titleBuilder.setSubtitle(mPersonBean.getData().getMin_time() + " - " + mPersonBean.getData().getMax_time());
            mHorBarCharts.showBarChart(horbarChart, getHorBarData((22 - 8) * 6, mPersonBean.getData().getStatic_lateral(), 0), true);


        }
    }

    public LineData getLinrData(List<Dashboard> dashboard) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        if (dashboard != null) {
            entries.add(new Entry(0, 0));
            for (int index = 0; index < 90; index++) {
                float v = 0;
                if (dashboard != null && dashboard.size() > index) {
                    v = Float.parseFloat(dashboard.get(index).getPercent());

                }
                entries.add(new Entry(index + 1, v));
            }
        }

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(0xff41adff);
        set.setLineWidth(2.5f);
        set.setDrawCircles(false);
        set.setCircleColor(0xff41adff);
        set.setCircleRadius(1f);

//        set.setFillColor(Color.rgb(240, 238, 70));
        set.setFillColor(0xff41adff);
        set.setDrawCircleHole(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
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
                value = Float.parseFloat(histogram.get(i).getSerious_time_percent());
                value2 = Float.parseFloat(histogram.get(i).getMiddle_time_percent());
                value3 = Float.parseFloat(histogram.get(i).getMild_time_percent());
            }
            if (j != 0) {
//                value = (float) (Math.random() * 10/*100以内的随机数*/);
                value = (float) (Math.random() * 10/*100以内的随机数*/);
                value2 = (float) (Math.random() * 10/*100以内的随机数*/);
                value3 = (float) (Math.random() * 10/*100以内的随机数*/);
            }
//            value = (float) (Math.random() * 10/*100以内的随机数*/);
//            value2 = (float) (Math.random() * 10/*100以内的随机数*/);
//            value3 = (float) (Math.random() * 10/*100以内的随机数*/);

            yValues.add(new BarEntry(i, new float[]{value, value2, value3}));

        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "测试图");
//        ArrayList<Integer> colors = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
////            colors.add(Color.parseColor("#75bfe2"));
//            colors.add(Color.parseColor("#41adff"));
//        }
        barDataSet.setColors(new int[]{Color.parseColor("#4dcdfd"), Color.parseColor("#7ddafb"), Color.parseColor("#b5ecff")});
        // 设置栏阴影颜色
        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
//        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
//        //可增加
//        barDataSets.add(barDataSet);

        barDataSet.setValueTextColor(Color.parseColor("#ffffff"));
        // 绘制值
        barDataSet.setDrawValues(false);
        BarData barData = new BarData(barDataSet);

        return barData;
    }

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(rlGuide_01)
                .setAlpha(150)
//                .setHighTargetCorner(20)
//                .setHighTargetPadding(10)
                .setOverlayTarget(true)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView2();
            }
        });

        builder.addComponent(new SimpleComponent(R.layout.guide_view_01));
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(this.getActivity());
    }

    public void showGuideView2() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(firstLayout)
                .setAlpha(150)
                .setHighTargetGraphStyle(Component.CIRCLE)
                .setOverlayTarget(false)
                .setExitAnimationId(android.R.anim.fade_out)
                .setOutsideTouchable(false);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
            }
        });

        builder1.addComponent(new SimpleComponent(R.layout.guide_view_02));
        Guide guide = builder1.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(this.getActivity());
    }


}
