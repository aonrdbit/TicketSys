package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.OrderToPassenger;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderToPassengerDao {
    public int insertOrderToPassenger(OrderToPassenger orderToPassenger);
}
