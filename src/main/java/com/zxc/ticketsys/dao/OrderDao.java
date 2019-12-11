package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {
    public Long insertOrder(Order order);
}
