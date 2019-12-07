package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class UserToPassenger {
    private long userId;
    private long psgId;

    public UserToPassenger(long userId, long psgId) {
        this.userId = userId;
        this.psgId = psgId;
    }
}
