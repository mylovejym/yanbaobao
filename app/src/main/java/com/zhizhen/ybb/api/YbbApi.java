package com.zhizhen.ybb.api;

import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.EyesightInfo;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.bean.PersonInfo;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @FormUrlEncoded
    @POST("/Api/Hardware/getPhoneCode")
    Observable<LoginBean> getPhoneCode(@Field("phone") String phone);

    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/register")
    Observable<LoginBean> getPhoneSuccess(@Field("phone") String phone,@Field("code") String code,@Field("pass") String pass);

    /**
     * 登录
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/login")
    Observable<LoginBean> login(@Field("phone") String phone, @Field("pass") String pass);

    /**
     * 忘记密码
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/forgetPass")
    Observable<LoginBean> forgetPass(@Field("phone") String phone,@Field("code") String code,@Field("pass") String pass);

    /**
     * 修改个人信息
     * @return
     */
    @Multipart
    @POST("/Api/Hardware/editPersonalInfo")
    Observable<BaseBean> editPersonalInfo(@Field("token")String token, @Field("username")String username, @Field("sex")String sex, @Field("born")String born, @Part("file\"; filename=\"photo.png\"") RequestBody photo);

    /**
     * 添加视力信息
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/addEyesightInfo")
    Observable<BaseBean> addEyesightInfo(@Field("token") String token, @Field("left_eye_degree")String left_eye_degree, @Field("right_eye_degree")String right_eye_degree, @Field("left_eye_astigmatism")String left_eye_astigmatism, @Field("right_eye_astigmatism")String right_eye_astigmatism, @Field("pupillary_distance")String pupillary_distance);

    /**
     * 返回视力信息
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/getEyesightInfo")
    Observable<BaseClassBean<EyesightInfo>> getEyesightInfo(@Field("token") String token);

    /**
     * 返回个人信息
     * @return
     */
    @FormUrlEncoded
    @POST("/Api/Hardware/getPersonInfo")
    Observable<BaseClassBean<PersonInfo>> getPersonInfo(@Field("token") String token);

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
