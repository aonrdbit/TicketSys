package com.zxc.ticketsys.model;

import com.zxc.ticketsys.utils.ComUtil;
import lombok.Data;

@Data
public class Passenger {
    private long psgId;
    private String Name;
    private String ID;
    private String phone;

    //add 带*身份证
    private String idx;

    public Passenger(String name, String ID, String phone) {
        this.Name = name;
        this.ID = ID;
        this.phone = phone;
    }

    public void setIdx(){
        this.idx= ComUtil.plusXing(ID);
    }
}
