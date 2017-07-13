package com.zhizhen.ybb.bean;

/**
 * Created by tc on 2017/7/13.
 */

public class UpdateInfo {

    private String version; //版本号
    private String downlink; //下载地址
    private String updatelog; //更新日志
    private String channelnum; //渠道号

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownlink() {
        return downlink;
    }

    public void setDownlink(String downlink) {
        this.downlink = downlink;
    }

    public String getUpdatelog() {
        return updatelog;
    }

    public void setUpdatelog(String updatelog) {
        this.updatelog = updatelog;
    }

    public String getChannelnum() {
        return channelnum;
    }

    public void setChannelnum(String channelnum) {
        this.channelnum = channelnum;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "version='" + version + '\'' +
                ", downlink='" + downlink + '\'' +
                ", updatelog='" + updatelog + '\'' +
                ", channelnum='" + channelnum + '\'' +
                '}';
    }
}
