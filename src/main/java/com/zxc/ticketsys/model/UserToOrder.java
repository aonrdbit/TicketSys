package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class UserToOrder {
    private long userId;
    private long orId;

    public UserToOrder(long userId, long orId) {
        this.userId = userId;
        this.orId = orId;
    }
}
