package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class Passenger {
    private long psgId;
    private String Name;
    private String ID;
    private String phone;

    public Passenger(String name, String ID, String phone) {
        Name = name;
        this.ID = ID;
        this.phone = phone;
    }
}
