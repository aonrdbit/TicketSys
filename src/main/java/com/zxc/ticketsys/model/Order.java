package com.zxc.ticketsys.model;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private long orId;
    private long trId;
    private String stSta;
    private String edSta;
    private double totalCost;
    private Date creTime;
    private int status;
}
