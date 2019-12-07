package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.Passenger;
import com.zxc.ticketsys.model.UserToPassenger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserToPassengerDao {
    public List<Long> selectPassengerByUserId(long userId);
    public int insertUserToPassenger(UserToPassenger userToPassenger);
}
