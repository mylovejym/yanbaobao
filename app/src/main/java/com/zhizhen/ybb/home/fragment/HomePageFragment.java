package com.zhizhen.ybb.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseFragment;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.GetStatistics;
import com.zhizhen.ybb.home.contract.HomeContract;
import com.zhizhen.ybb.home.model.HomePageModel;
import com.zhizhen.ybb.home.presenter.HomePagePresenter;
import com.zhizhen.ybb.lanya.MyBLEActivity;
import com.zhizhen.ybb.view.MyDialChart;

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
    com.zhizhen.ybb.view.SecondChart SecondChart;
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

//    LoopRecyclerViewPager vpTop;

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

    }


    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showData(BaseClassBean<GetStatistics> mPersonBean) {
        if (mPersonBean.getStatus().equals("0")) {
            if(mPersonBean.getData().getDashboard()!=null){
                myDialChart.put(mPersonBean.getData().getDashboard());
            }

        }
    }


}
