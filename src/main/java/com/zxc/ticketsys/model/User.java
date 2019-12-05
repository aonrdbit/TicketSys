package com.zxc.ticketsys.model;

import lombok.Data;

@Data
public class User {
    private long userId;
    private String userName;
    private String passWord;
    private String Name;
    private String ID;
    private String phone;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
}
