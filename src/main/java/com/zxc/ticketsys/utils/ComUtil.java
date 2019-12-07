package com.zxc.ticketsys.utils;

public class ComUtil {
    private static String regex="(\\w{6})(\\w+)(\\w{4})";
    public static String plusXing (String s) {
        return s.replaceAll(regex, "$1********$3");
    }
}
