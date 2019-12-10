package com.zxc.ticketsys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ComUtil {
    private static String regex="(\\w{6})(\\w+)(\\w{4})";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static HashMap<String,Integer> hs=new HashMap<>();
    static{
        hs.put("Jan",1);
        hs.put("Feb",2);
        hs.put("Dec",12);
    }
    public static String plusXing (String s) {
        return s.replaceAll(regex, "$1********$3");
    }

    public static Date strToDate(String s) throws ParseException {
        String[] ss=s.split(" ");
        return sdf.parse(ss[3]+"-"+hs.get(ss[1])+"-"+ss[2]);
    }
}
