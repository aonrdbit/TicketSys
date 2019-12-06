package com.zxc.ticketsys.model;

import lombok.Data;

import java.sql.Time;

@Data
public class TrainTime {
    private String trNo;
    private Time stTime;
}
