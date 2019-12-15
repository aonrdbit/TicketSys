package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class TrainToSeat {
    private long trId;
    private String seNo;
    private int idx;
    private int status;

    public TrainToSeat(){}
    public TrainToSeat(long trId, String seNo, int idx,int status) {
        this.trId = trId;
        this.seNo = seNo;
        this.idx = idx;
        this.status=status;
    }
}

