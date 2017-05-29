package com.zhizhen.ybb.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseFragment;
import com.zhizhen.ybb.lanya.MyBLEActivity;


/**
 * Created by psylife00 on 2016/12/20.
 */

public class HomePageFragment extends YbBaseFragment {

//    LoopRecyclerViewPager vpTop;

    @Override
    public View getTitleView() {
        return new TitleBuilder(getActivity())
                .setRightText("蓝牙")
                .setTitleBgRes(R.color.blue_313245)
                .setRightOnClickListener(v -> {
                    startActivity(new Intent(getActivity(),MyBLEActivity.class));
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



}
