package com.zhizhen.ybb.my;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.zhizhen.ybb.R;
import com.zhizhen.ybb.R2;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyModel;
import com.zhizhen.ybb.my.presenter.MyPresenter;
import com.zhizhen.ybb.view.WaveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：tc on 2017/5/11.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class BindingActivity extends YbBaseActivity<MyPresenter, MyModel> implements MyContract.GetPersonInfoView, View.OnClickListener {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mWaveView.setText(a + "%");
            if (a != 100) {
                mWaveView.setTextState("正在绑定...");
            } else {
                mWaveView.setTextState("绑定成功");
//                mWaveView.setWaveShiftRatio(1f);
//                mAnimatorSet.end();
            }

        }
    };

    private int a = 0;

    @BindView(R2.id.costom_round)
    WaveView mWaveView;

    private AnimatorSet mAnimatorSet;

    @Override
    public int getLayoutId() {
        return R.layout.activity_binding;
    }

    @Override
    public View getTitleView() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_title_bar, null);
        return view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mWaveView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initAnimation();
        mWaveView.setShowWave(true);
        if (mAnimatorSet != null) {
            mAnimatorSet.start();
        }

        new Thread() {
            @Override
            public void run() {

                while (a < 100) {
                    try {
                        if (a == 50) {
                            Thread.sleep(2000);
                        }
                        Thread.sleep(100);
                        mHandler.sendEmptyMessage(0);
                        a++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void initdata() {

    }

    private void initAnimation() {
        List<Animator> animators = new ArrayList<>();

        // horizontal animation.
        // wave waves infinitely.
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                mWaveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        // vertical animation.
        // water level increases from 0 to center of WaveView
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                mWaveView, "waterLevelRatio", 0.4f, 0.4f);
        waterLevelAnim.setDuration(10000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        animators.add(waterLevelAnim);

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                mWaveView, "amplitudeRatio", 0.0001f, 0.05f);
        amplitudeAnim.setRepeatCount(ValueAnimator.RESTART);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(50 * 100);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showPersonInfo(PersonInfo mPersonInfo) {

    }
}
