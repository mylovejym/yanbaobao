package com.zhizhen.ybb.bean;

/**
 * 个人信息
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class PersonBean {
    private String status;
    private String statusInfo;
    private PersonInfo data;

    public PersonInfo getData() {
        return data;
    }

    public void setData(PersonInfo data) {
        this.data = data;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class PersonInfo {
        private String username;  //名称
        private String sex;       //性别
        private String born;      //出生年月
        private String photo;     //头像

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

        @Override
        public String toString() {
            return "PersonBean{" +
                    "username='" + username + '\'' +
                    ", sex='" + sex + '\'' +
                    ", born='" + born + '\'' +
                    ", photo='" + photo + '\'' +
                    '}';
        }
    }
}
