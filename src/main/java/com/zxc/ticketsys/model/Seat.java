package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class Seat {
    private String trNo;
    private String seNo;
    private int seLev;
    private double sePrice;
}
