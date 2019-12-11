package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class OrderToPassenger {
    private long orId;
    private long psgId;
    private String seNo;

    public OrderToPassenger(long orId, long psgId, String seNo) {
        this.orId = orId;
        this.psgId = psgId;
        this.seNo = seNo;
    }
}
