package com.example.liyayu.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @Description: 输入法显示和隐藏
 * @Copyright: Copyright (c) 2016 chexiang.com. All right reserved.
 * @Author: songjunpeng
 * @Date: 2016/4/5 17:19
 * @Modifier: songjunpeng
 * @Update: 2016/4/5 17:19
 */
public class InputUtils {
    /**
     * 收起输入法
     */
    public static void hideInput(Activity a) {
        hideInput(a, a.getCurrentFocus());
    }

    /**
     * @param a
     * @param v
     */
    public static void hideInput(Activity a, View v) {
        if (a != null) {
            //获得软键盘的控制类
            InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            //现在的焦点不为null
            if (v != null) {
                //收起软键盘
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 传入一个布局进来
     *
     * @param v
     * @param a
     */
    public static void hideInput(View v, final Activity a) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideInput(a);
                return false;
            }
        });
    }


    private static void showInput(EditText v) {
        //获得软键盘的控制类
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //现在的焦点不为null
        //打开软键盘
//            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        imm.showSoftInput(v, 0);
    }

    public static void changeFocus(final EditText forwardId, final EditText nextId) {
        if (forwardId != null && nextId != null) {
            forwardId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        nextId.requestFocus();
                    }
                    return true;
                }
            });
        }
    }

    public static void setFocus(final EditText id) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                id.setFocusableInTouchMode(true);
                id.requestFocus();
                showInput(id);
            }
        }, 498);
    }
}
