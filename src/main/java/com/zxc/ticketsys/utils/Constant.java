package com.zxc.ticketsys.utils;

import com.zxc.ticketsys.dao.TrainToSeatDao;
import com.zxc.ticketsys.model.TrainToSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Constant {
    public static List<String> seNos=new ArrayList<>();
    public static char[] abcef=new char[]{'A','B','C','E','F'};
    public static int sz=10;
    public static List<Date> dates=new ArrayList<>();
    static{
        for(int i=1;i<=3;i++){
            for(int j=1;j<=2;j++){
                String s="0"+i+"-";
                if(j<10){
                    s+="0";
                }
                s+=j;
                for(int k=0;k<5;k++){
                    seNos.add(s+abcef[k]);
                }

            }

        }
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

        Date d= null;
        try {
            d = dateFormat1.parse("2019-12-27");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date m= null;
        try {
            m = dateFormat1.parse("2020-01-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(Date dd=d;dd.compareTo(m)<0;){
            dates.add(dd);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dd);
            calendar.add(Calendar.DATE, 1);
            dd = calendar.getTime();
        }
    }
}
