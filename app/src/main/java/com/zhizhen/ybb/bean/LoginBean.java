package com.zhizhen.ybb.bean;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class LoginBean {

    private String status;
    private String statusInfo;
    private LoginData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    class LoginData {
        private String token;
        private String username; //用户名
        private String sex;//性别
        private String born; //出生日期
        private String photo;//头像
        private String left_eye_degree;//左眼视力
        private String right_eye_degree;//右眼视力
        private String left_eye_astigmatism;//左眼散光
        private String right_eye_astigmatism;//右眼散光
        private String pupillary_distance;//两眼瞳距

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBorn() {
            return born;
        }

        public void setBorn(String born) {
            this.born = born;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getLeft_eye_degree() {
            return left_eye_degree;
        }

        public void setLeft_eye_degree(String left_eye_degree) {
            this.left_eye_degree = left_eye_degree;
        }

        public String getRight_eye_degree() {
            return right_eye_degree;
        }

        public void setRight_eye_degree(String right_eye_degree) {
            this.right_eye_degree = right_eye_degree;
        }

        public String getLeft_eye_astigmatism() {
            return left_eye_astigmatism;
        }

        public void setLeft_eye_astigmatism(String left_eye_astigmatism) {
            this.left_eye_astigmatism = left_eye_astigmatism;
        }

        public String getRight_eye_astigmatism() {
            return right_eye_astigmatism;
        }

        public void setRight_eye_astigmatism(String right_eye_astigmatism) {
            this.right_eye_astigmatism = right_eye_astigmatism;
        }

        public String getPupillary_distance() {
            return pupillary_distance;
        }

        public void setPupillary_distance(String pupillary_distance) {
            this.pupillary_distance = pupillary_distance;
        }
    }

}
