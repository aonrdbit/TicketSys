package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.Passenger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PassengerDao {
    public int insertPassenger(Passenger passenger);
    public int selectCountID(String ID);
    public Passenger selectPassengerInfoByPsgId(long psgId);
    public long selectPsgIdByID(String ID);
    public int delPassengerBy(Long psgId);
}
