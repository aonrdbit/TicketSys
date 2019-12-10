package com.zxc.ticketsys.utils;

import java.sql.Time;
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

    public static String diffHour(Time begin, Time end) {
        long i = begin.getTime();
        long j = end.getTime();
        String str="";
        if(i>j){
            j += 86400000l;
            str="次日到达";
        }else{
            str="当日到达";
        }
        System.out.println((i-j)/1000);
        long t=Math.abs(i-j)/1000;
        long d=t/3600;
        String ds=d+"";
        if(d<10){
            ds="0"+ds;
        }
        long m=(t%3600)/60;
        String ms=m+"";
        if(m<10){
            ms="0"+m;
        }
        return ds+":"+ms+" "+str;
    }

}
