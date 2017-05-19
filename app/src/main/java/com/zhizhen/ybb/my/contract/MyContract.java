package com.zhizhen.ybb.my.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.EyesightInfo;
import com.zhizhen.ybb.bean.PersonInfo;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        Observable<BaseClassBean<PersonInfo>> getPersonInfo(String token);
    }

    interface MyView extends WRBaseView {
        void showPersonInfo(BaseClassBean<PersonInfo> mPersonBean);
    }

    //我的视力
    abstract class MyVisonPresenter extends YbBasePresenter<MyVisonModel, MyVisonView> {
        /**
         * 获取视力信息
         *
         * @param token
         */
        public abstract void getEyesightInfo(String token);

        /**
         * 添加视力信息
         *
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
        Observable<BaseClassBean<EyesightInfo>> getEyesightInfo(String token);

        Observable<BaseBean> addEyesightInfo(String token, String left_eye_degree, String right_eye_degree, String left_eye_astigmatism, String right_eye_astigmatism, String pupillary_distance);
    }

    interface MyVisonView extends WRBaseView {
        void showEyesightInfo(BaseClassBean<EyesightInfo> mEyesightBean);

        void showAddEyesightInfo(BaseBean baseBean);
    }


    //编辑资料
    abstract class EditDataPresenter extends YbBasePresenter<EditDataModel, EditDataView> {
        /**
         * 修改个人信息
         */
        public abstract void editPersonalInfo(String token, String username, String sex, String born, String photo);
    }

    interface EditDataModel extends WRBaseModel {
        Observable<BaseBean> editPersonalInfo(String token, String username, String sex, String born, String photo);
    }

    interface EditDataView extends WRBaseView {
        void showEditDataInfo(BaseBean baseBean);
    }


}
