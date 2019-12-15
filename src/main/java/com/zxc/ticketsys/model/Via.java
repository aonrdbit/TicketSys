package com.zxc.ticketsys.model;

import lombok.Data;

import java.sql.Time;

@Data
public class Via {
    private String trNo;
    private String stationA;
    private String stationB;
    private int idx;
    private Time staTime;
    private Time arrTime;
    private int dwellTime;
    private double firPrice;
    private double secPrice;

    public Via(String trNo, String stationA, String stationB, int idx, Time staTime,Time arrTime, int dwellTime, double firPrice, double secPrice) {
        this.trNo = trNo;
        this.stationA = stationA;
        this.stationB = stationB;
        this.idx = idx;
        this.staTime=staTime;
        this.arrTime = arrTime;
        this.dwellTime = dwellTime;
        this.firPrice = firPrice;
        this.secPrice = secPrice;
    }
}
