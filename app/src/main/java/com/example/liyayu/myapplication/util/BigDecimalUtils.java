package com.example.liyayu.myapplication.util;

import java.math.BigDecimal;

/**
 * @Description: 金额计算
 * @Copyright: Copyright (c) 2017 chexiang.com. All right reserved.
 * @Author: zhangshunjie
 * @Date: 2017/7/29 17:49
 * @Modifier: zhangshunjie
 * @Update: 2017/7/29 17:49
 */
public class BigDecimalUtils {

    // +
    public static String add(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(StringUtils.isBlank(v1) ? "0" : v1);
            BigDecimal b2 = new BigDecimal(StringUtils.isBlank(v2) ? "0" : v2);
//            return b1.add(b2).toString();
            return b1.add(b2).stripTrailingZeros().toPlainString();//去掉无用的小数点以及末尾的0
        } catch (Exception e) {
            LogUtil.e(e);
            return "0";
        }
    }

    // +
    public static String add(String... v1) {
        try {
            BigDecimal a1 = new BigDecimal("0");
            BigDecimal b1;
            for (String s : v1) {
                b1 = new BigDecimal(StringUtils.isBlank(s) ? "0" : s);
                a1 = a1.add(b1);
            }
            return a1.toString();
        } catch (Exception e) {
            LogUtil.e(e);
            return "0";
        }
    }

    // -
    public static String subtract(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(StringUtils.isBlank(v1) ? "0" : v1);
            BigDecimal b2 = new BigDecimal(StringUtils.isBlank(v2) ? "0" : v2);
            return b1.subtract(b2).stripTrailingZeros().toPlainString();//去掉无用的小数点以及末尾的0
        } catch (Exception e) {
            LogUtil.e(e);
            return "0";
        }
    }

    /**
     * -
     *
     * @param v1
     * @param v2
     * @param scale 保留小数位数，采用四舍五入方式
     * @return
     */
    public static String subtract(String v1, String v2, int scale) {
        try {
            BigDecimal b1 = new BigDecimal(StringUtils.isBlank(v1) ? "0" : v1);
            BigDecimal b2 = new BigDecimal(StringUtils.isBlank(v2) ? "0" : v2);
            return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            LogUtil.e(e);
            return "0";
        }
    }

    // *
    public static String multiply(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(StringUtils.isBlank(v1) ? "0" : v1);
            BigDecimal b2 = new BigDecimal(StringUtils.isBlank(v2) ? "0" : v2);
            return b1.multiply(b2).toString();
        } catch (Exception e) {
            LogUtil.e(e);
            return "0";
        }
    }

    /**
     * @param v1
     * @param v2
     * @param scale 保留小数位数，采用四舍五入方式
     * @return
     */
    public static String multiply(String v1, String v2, int scale) {
        try {
            BigDecimal b1 = new BigDecimal(StringUtils.isBlank(v1) ? "0" : v1);
            BigDecimal b2 = new BigDecimal(StringUtils.isBlank(v2) ? "0" : v2);
            return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            LogUtil.e(e);
            return "0";
        }
    }

    // 比大小，-1：小于 0：等于 1：大于
    public static int compareTo(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(StringUtils.isBlank(v1) ? "0" : v1);
            BigDecimal b2 = new BigDecimal(StringUtils.isBlank(v2) ? "0" : v2);
            return b1.compareTo(b2);
        } catch (Exception e) {
            LogUtil.e(e);
            return 0;
        }
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */

    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            LogUtil.e(e);
            return "0";
        }
    }

    // 转 int 数值
    public static int convertInt(String v) {
        try {
            if (v == null || "".equals(v)) {
                return 0;
            }
            return Integer.parseInt(v);
        } catch (Exception e) {
            LogUtil.e(e);
            return 0;
        }
    }

    // 转 long 数值
    public static long convertLong(String v) {
        try {
            if (v == null || "".equals(v)) {
                return 0;
            }
            return Long.parseLong(v);
        } catch (Exception e) {
            LogUtil.e(e);
            return 0;
        }
    }

    // 转 double 数值
    public static double convertDouble(String v) {
        try {
            if (v == null || "".equals(v)) {
                return 0;
            }
            return Double.parseDouble(v);
        } catch (Exception e) {
            LogUtil.e(e);
            return 0;
        }
    }

}
