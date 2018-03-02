package com.example.liyayu.myapplication.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(Context context, int msg) {
        showToast(context, context.getString(msg));
    }

    public static void showToast(Context context, String msg) {
        if (msg == null || "".equals(msg)||context==null) {
            return;
        }
        int duration = msg.length() > 15 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

}