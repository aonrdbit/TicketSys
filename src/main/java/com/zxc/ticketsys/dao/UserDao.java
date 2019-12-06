package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public String selectPassWordByUserName(String userName);
    public int insertUser(User user);
    public User selectUserByUserName(String userName);
}
