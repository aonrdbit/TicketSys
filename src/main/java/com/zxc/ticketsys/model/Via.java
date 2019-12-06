package com.zxc.ticketsys.model;

import lombok.Data;

import java.sql.Time;

@Data
public class Via {
    private String trNo;
    private String stationA;
    private String stationB;
    private Time arrTime;
    private int dwellTime;
    private double firPrice;
    private double secPrice;
}
