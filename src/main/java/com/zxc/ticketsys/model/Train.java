package com.zxc.ticketsys.model;

import lombok.Data;

import java.util.Date;

@Data
public class Train {
    private long trId;
    private String trNo;
    private Date trDate;
}
