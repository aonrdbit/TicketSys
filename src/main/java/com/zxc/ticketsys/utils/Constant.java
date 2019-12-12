package com.zxc.ticketsys.utils;

import com.zxc.ticketsys.model.TrainToSeat;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static List<String> seNos=new ArrayList<>();
    public static char[] abcef=new char[]{'A','B','C','E','F'};
    static{
        for(int i=1;i<=4;i++){
            for(int j=1;j<=8;j++){
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
    }
}
