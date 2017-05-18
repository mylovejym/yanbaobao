package com.zhizhen.ybb.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.zhizhen.ybb.view.ActionSheetDialog;

import java.io.File;

/**
 * Created by tc on 2016/7/18.
 */
public class DialogUtils {

    private static Activity activity;

    /**
     * 拍照的照片存储位置
     **/
    private static final String FILEROOTDIRECTORY = Environment
            .getExternalStorageDirectory().getPath()
            + "/ybb/ybb/ybbImage/";

    /**
     * 父目录
     **/
    private static File parent_file = new File(FILEROOTDIRECTORY);
    /**
     * 以当前时间作为照片文件名
     **/
    public static File currentFileName = null;

    /* 显示发布新商品对话框 */
    public static void showTakePhotoDialog(Activity activitys) {
        activity = activitys;
        final ActionSheetDialog dialog = new ActionSheetDialog(activity);
        dialog.builder();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnClick(new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                if (which == ActionSheetDialog.TAKE_PHOTO) {
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        if (!parent_file.exists()) {
                            parent_file.mkdirs();
                        }
                        currentFileName = new File(FILEROOTDIRECTORY + TakePhotosDispose.getPhotoFileName());
                        activity.startActivityForResult(TakePhotosDispose.takePhotoAction(Uri.fromFile
                                        (currentFileName)),
                                TakePhotosDispose.TAKEPHOTO);
                    } else {
                        Toast.makeText(activity, "无SDK或者存储空间已满", Toast.LENGTH_SHORT).show();
                    }
                } else if (which == ActionSheetDialog.TAKE_FILE) {
                    try {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        activity.startActivityForResult(i,
                                TakePhotosDispose.PICKPHOTO);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(activity, "无相片", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
