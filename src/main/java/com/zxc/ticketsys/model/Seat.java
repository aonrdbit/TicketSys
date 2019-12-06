package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class Seat {
    private long seId;
    private long trId;
    private String seNo;
    private int seLev;
    private double sePrice;
}
