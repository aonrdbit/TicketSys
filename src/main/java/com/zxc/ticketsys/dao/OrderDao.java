package com.zxc.ticketsys.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zxc.ticketsys.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {
    public Long insertOrder(Order order);
    public List<Order> selectAllOrderByUserId(@Param("userId") Long userId);
    public Long selectTrIdByOrId(Long orId);
    public Order selectOrderByOrId(Long orId);
}
