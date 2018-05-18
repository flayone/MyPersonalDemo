package com.example.liyayu.myapplication.customWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by liyayu on 2018/4/19.
 * 可使用矢量图标资源的text布局
 */

public class IconFontTextView extends android.support.v7.widget.AppCompatTextView {
    public IconFontTextView(Context context) {
        super(context);
        init(context);
    }
    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public IconFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context) {
        //加载字体文件
        Typeface typeface = IconFontTypeFace.getTypeface(context);
        this.setTypeface(typeface);
        //去掉padding,这样iconfont和普通字体容易对齐
        setIncludeFontPadding(false);
    }
    public static class IconFontTypeFace {
        //用static,整个app共用整个typeface就够了
        private static Typeface ttfTypeface = null;
        static synchronized Typeface getTypeface(Context context) {
            if (ttfTypeface == null) {
                try {
                    //context.getApplicationContext()防止内存泄漏
                    ttfTypeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/iconfont.ttf");
                } catch (Exception ignored) {
                }
            }
            return ttfTypeface;
        }
        public static synchronized void clearTypeface() {
            ttfTypeface = null;
        }
    }
}
