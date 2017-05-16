package com.zhizhen.ybb.api;

import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.EyesightBean;
import com.zhizhen.ybb.bean.PersonBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public interface YbbApi {

    /**
     * 发送验证码
     * @return
     */
    @POST("/Api/Hardware/getPhoneCode")
    Observable<Object> getPhoneCode();

    /**
     * 注册
     * @return
     */
    @POST("/Api/Hardware/register")
    Observable<Object> register();

    /**
     * 登录
     * @return
     */
    @POST("/Api/Hardware/login")
    Observable<Object> login(Object obj);

    /**
     * 忘记密码
     * @return
     */
    @POST("/Api/Hardware/forgetPass")
    Observable<Object> forgetPass();

    /**
     * 修改个人信息
     * @return
     */
    @POST("/Api/Hardware/editPersonalInfo")
    Observable<Object> editPersonalInfo();

    /**
     * 添加视力信息
     * @return
     */
    @POST("/Api/Hardware/addEyesightInfo")
    Observable<BaseBean> addEyesightInfo(@Field("token") String token, @Field("left_eye_degree")String left_eye_degree, @Field("right_eye_degree")String right_eye_degree, @Field("left_eye_astigmatism")String left_eye_astigmatism, @Field("right_eye_astigmatism")String right_eye_astigmatism, @Field("pupillary_distance")String pupillary_distance);

    /**
     * 返回视力信息
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/getEyesightInfo")
    Observable<EyesightBean> getEyesightInfo(@Field("token") String token);

    /**
     * 返回个人信息
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/getPersonInfo")
    Observable<PersonBean> getPersonInfo(@Field("token") String token);

    /**
     * 关注眼专家
     * @return
     */
    @POST("/Api/Hardware/focusMe")
    Observable<Object> focusMe();

    /**
     * APP写入数据
     * @return
     */
    @POST("/Api/Hardware/addHardwareData")
    Observable<Object> addHardwareData();

    /**
     * 统计数据接口
     * @return
     */
    @POST("/Api/Hardware/static_data")
    Observable<Object> static_data();
}
