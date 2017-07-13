package com.zhizhen.ybb.loginpass;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.UpdateInfo;
import com.zhizhen.ybb.home.HomeActivity2;
import com.zhizhen.ybb.loginpass.contract.UpdateContract.UpdateView;
import com.zhizhen.ybb.loginpass.model.UpdateModel;
import com.zhizhen.ybb.loginpass.presenter.UpdatePresenter;
import com.zhizhen.ybb.util.SpUtils;
import com.zhizhen.ybb.util.UpdaterUtils;

public class WelcomeActivity extends YbBaseActivity<UpdatePresenter, UpdateModel> implements UpdateView {

    private AlertDialog dialogs;

    DownloadManager downloadManager;

    @Override
    public int getLayoutId() {
        return R.layout.welcom;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.background_color));
    }

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    public void initdata() {
        startProgressDialog(this);
        try {
            PackageInfo packageInfo = getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            //获取APP版本versionName
            String versionName = packageInfo.versionName;
            //获取APP版本versionCode
            int versionCode = packageInfo.versionCode;

            System.out.println("v = " + versionName + "| =" + versionCode);

            String channel = "zhizhen";

            mPresenter.checkNewestVersion("" + versionName, channel);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {
        this.stopProgressDialog();
        Toast.makeText(this, "网络错误，请稍后再试", Toast.LENGTH_SHORT).show();
        LogUtil.d("e==================>>>" + e);
    }

    @Override
    public void showContent(UpdateInfo updateInfo) {
        this.stopProgressDialog();
        if (updateInfo.getVersion() == null || updateInfo.getVersion().equals("")) {
            next();
        } else {
            showDialog(updateInfo);
        }
    }

    public void next() {
        // 处理接收到的消息的方法
        new Handler(arg0 -> {
            boolean isFirstLogin = SpUtils.getBoolean(WelcomeActivity.this, "firstLogin");
            if (isFirstLogin) {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,
                        HomeActivity2.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
            return false;
        }).sendEmptyMessageDelayed(0, 3000); // 表示延时三秒进行任务的执行
    }

    /**
     * 提示框
     *
     * @param updateInfo
     */
    public void showDialog(UpdateInfo updateInfo) {
        dialogs = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(updateInfo.getUpdatelog())
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        (dialog, which) -> {
                            if (UpdaterUtils.getLocalDownloadId(this) < 0) {
                                UpdaterUtils.saveDownloadId(this, startDownload(updateInfo));
                                Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                                next();
                            } else {
                                next();
                                UpdaterUtils.installApk(this, UpdaterUtils.getLocalDownloadId(this));
                            }
                        })
                .setNegativeButton("暂不更新", (dialog, which) -> {
                    dialogs.dismiss();
                    next();
                }).create();// 创建
        // 显示对话框
        dialogs.show();
    }

    public long startDownload(UpdateInfo updateInfo) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(updateInfo.getDownlink()));

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //req.setAllowedOverRoaming(false);

        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //设置文件的保存的位置[三种方式]
        //第一种
        //file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
        req.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第二种
        //file:///storage/emulated/0/Download/update.apk
        //req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第三种 自定义文件路径
        //req.setDestinationUri()

        // 设置一些基本显示信息
        req.setTitle(this.getResources().getString(R.string.app_name));
        req.setDescription("");
        req.setMimeType("application/vnd.android.package-archive");

        //加入下载队列
        return downloadManager.enqueue(req);
    }

}
