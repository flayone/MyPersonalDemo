package com.example.liyayu.myapplication.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.liyayu.myapplication.BuildConfig;

/**
 * Created by liyayu on 2018/2/8.
 */
public class LogUtil {
    /**
     * 默认的文库日志Tag标签
     */
    public final static String DEFAULT_TAG = "LogUtil";

    /**
     * 此常量用于控制是否打日志到Logcat中 release版本中本变量应置为false
     */

    private final static boolean LOGGABLE = BuildConfig.DEBUG;


    /**
     * 打印debug级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void d(final String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.d(tag, str);
                }
            });
        }
    }

    /**
     * 打印debug级别的log
     *
     * @param str 内容
     */
    public static void d(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.d(DEFAULT_TAG, str);
                }
            });
        }
    }

    /**
     * 打印超长log
     *
     * @param str      超长log字符串
     * @param callBack 打印回调
     */
    private static void moreLog(String str, LogCallBack callBack) {
        //系统log 默认最大 4 * 1024 ，这里稍微缩小大小
        int segmentSize = 4 * 1000;
        long length = str.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            callBack.logCallBackFun(str);
        } else {
            while (str.length() > segmentSize) {// 循环分段打印日志
                String logContent = str.substring(0, segmentSize);
                str = str.replace(logContent, "");
                callBack.logCallBackFun(logContent);
            }
            callBack.logCallBackFun(str);// 打印剩余日志
        }
    }

    /**
     * 打印warning级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void w(final String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.w(tag, str);
                }
            });
        }
    }

    /**
     * 打印warning级别的log
     *
     * @param str 内容
     */
    public static void w(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.w(DEFAULT_TAG, str);
                }
            });
        }
    }

    /**
     * 打印error级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void e(final String tag, String str, final Throwable tr) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.e(tag, str);
                    tr.printStackTrace();
                }
            });
        }
    }

    /**
     * 打印error级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void e(final String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.e(tag, str);
                }
            });
        }
    }

    /**
     * 打印error级别的log
     *
     * @param str 内容
     */
    public static void e(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.e(DEFAULT_TAG, str);
                }
            });
        }
    }

    /**
     * 打印error级别的log
     */
    public static void e(final Throwable e) {
        if (LOGGABLE) {
            moreLog(e.toString(), new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.e(DEFAULT_TAG, null, e);
                }
            });
        }
    }

    /**
     * 打印error级别的log
     */
    public static void e(final Throwable e, String str) {
        if (LOGGABLE) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.e(DEFAULT_TAG, str, e);
                }
            });
        }
    }

    /**
     * 打印info级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void i(final String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.i(tag, str);
                }
            });
        }
    }

    /**
     * 打印info级别的log
     *
     * @param str 内容
     */
    public static void i(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.i(DEFAULT_TAG, str);
                }
            });
        }
    }

    /**
     * 打印verbose级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void v(final String tag, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.v(tag, str);
                }
            });
        }
    }

    /**
     * 打印verbose级别的log
     *
     * @param str 内容
     */
    public static void v(String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
            moreLog(str, new LogCallBack() {
                @Override
                public void logCallBackFun(@NonNull String str) {
                    Log.v(DEFAULT_TAG, str);
                }
            });
        }
    }

    /**
     * 将log写入文件(/data/data/package name/files/log)
     *
     * @param str 内容
     */
    public static void flood(Context context, String str) {
        if (LOGGABLE && !TextUtils.isEmpty(str)) {
//			str += "\n";
//			FileUtils.writeToFile(context, str.getBytes(), "/log", true);
        }
    }

    interface LogCallBack {
        void logCallBackFun(String str);
    }
}

