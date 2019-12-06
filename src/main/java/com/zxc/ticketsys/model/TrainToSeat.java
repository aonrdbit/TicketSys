package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class TrainToSeat {
    private long trId;
    private String seNo;
    private int status;
}

