package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class OrderToPassenger {
    private long orId;
    private long psgId;
    private String seNo;
}
