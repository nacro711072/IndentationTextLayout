package com.nacro.indent;

/**
 * Created by terry on 2018/7/24.
 */

class NumToChineseNum {
    private static final String[] chineseNum = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    public static String toChineseNum(String num) {
//        不支援100以上
        if (Integer.valueOf(num) == null || Integer.valueOf(num) < 0 || num.length() > 2) {
            return null;
        }

        if (num.length() == 1) {
            return chineseNum[Integer.valueOf(num)];
        } else {
            String ten = num.substring(0, 1);
            String one = num.substring(1, 2);
            if (ten.equals("1")) {
                if (one.equals("0")) {
                    return "十";
                } else {
                    return "十" + chineseNum[Integer.valueOf(one)];
                }
            } else {
                return chineseNum[Integer.valueOf(ten)] + "十" + chineseNum[Integer.valueOf(one)];
            }
        }
    }

    public static String toChineseNum(int num) {
        return toChineseNum(String.valueOf(num));
    }

}
