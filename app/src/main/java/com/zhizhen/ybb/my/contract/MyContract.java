package com.zhizhen.ybb.my.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.EyesightBean;
import com.zhizhen.ybb.bean.PersonBean;

import rx.Observable;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public interface MyContract {

    //My界面获取个人信息
    abstract class MyPresenter extends YbBasePresenter<MyModel, MyView> {
        public abstract void getPersonInfo(String token);

    }

    interface MyModel extends WRBaseModel {
        Observable<PersonBean> getPersonInfo(String token);
    }

    interface MyView extends WRBaseView {
        void showPersonInfo(PersonBean mPersonInfo);
    }

    //我的视力
    abstract class MyVisonPresenter extends YbBasePresenter<MyVisonModel, MyVisonView> {
        /**
         * 获取视力信息
         * @param token
         */
        public abstract void getEyesightInfo(String token);

        /**
         * 添加视力信息
         * @param token
         * @param left_eye_degree
         * @param right_eye_degree
         * @param left_eye_astigmatism
         * @param right_eye_astigmatism
         * @param pupillary_distance
         */
        public abstract void addEyesightInfo(String token, String left_eye_degree, String right_eye_degree, String left_eye_astigmatism, String right_eye_astigmatism, String pupillary_distance);

    }

    interface MyVisonModel extends WRBaseModel {
        Observable<EyesightBean> getEyesightInfo(String token);
        Observable<BaseBean> addEyesightInfo(String token, String left_eye_degree, String right_eye_degree, String left_eye_astigmatism, String right_eye_astigmatism, String pupillary_distance);
    }

    interface MyVisonView extends WRBaseView {
        void showEyesightInfo(EyesightBean mPersonInfo);
        void showAddEyesightInfo(BaseBean baseBean);
    }


}
