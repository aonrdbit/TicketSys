package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.OrderToPassenger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderToPassengerDao {
    public int insertOrderToPassenger(OrderToPassenger orderToPassenger);
    public List<OrderToPassenger> selectPsgByOrId(Long orId);
    public int updateStatusSetFour(@Param("orId") Long orId,@Param("psgId")Long psgId);
    public List<Long> selectOrIdByPsgId(Long psgId);
}
