package com.zhizhen.ybb.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhizhen.ybb.R;

/**
 * 底部对话框样式
 */
public class ActionSheetDialog {
    private Context context;
    private Dialog dialog;
    private TextView txt_cancel, txt_takePhoto, txt_takeFile;
    private Display display;

    public final static int TAKE_PHOTO = 0;
    public final static int TAKE_FILE = 1;

    private OnSheetItemClickListener mClickListener;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    @SuppressWarnings("deprecation")
    public ActionSheetDialog builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_bottom, null);

        view.setMinimumWidth(display.getWidth());

        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(v -> dialog.dismiss());
        txt_takePhoto = (TextView) view.findViewById(R.id.txt_takePhoto);
        txt_takePhoto.setOnClickListener(v -> mClickListener.onClick(TAKE_PHOTO));
        txt_takeFile = (TextView) view.findViewById(R.id.txt_choosePhoto);
        txt_takeFile.setOnClickListener(v -> mClickListener.onClick(TAKE_FILE));

        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setOnClick(OnSheetItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }
}
