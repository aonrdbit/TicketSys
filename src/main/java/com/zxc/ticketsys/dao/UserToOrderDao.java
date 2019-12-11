package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.UserToOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserToOrderDao {
    public int insertUserToOrder(UserToOrder userToOrder);
}
