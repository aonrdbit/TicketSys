package com.zxc.ticketsys.model;

import lombok.Data;

import java.util.Date;

@Data
public class Via {
    private String trNo;
    private String stationA;
    private String stationB;
    private Date arrTime;
    private Date dwellTime;
}
