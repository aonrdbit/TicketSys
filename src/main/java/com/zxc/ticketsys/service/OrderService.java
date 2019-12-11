package com.zxc.ticketsys.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zxc.ticketsys.dao.OrderDao;
import com.zxc.ticketsys.dao.OrderToPassengerDao;
import com.zxc.ticketsys.dao.UserToOrderDao;
import com.zxc.ticketsys.model.Order;
import com.zxc.ticketsys.model.OrderToPassenger;
import com.zxc.ticketsys.model.User;
import com.zxc.ticketsys.model.UserToOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderToPassengerDao orderToPassengerDao;
    @Autowired
    private UserToOrderDao userToOrderDao;
    /**
     * 关联的表：order order_passenger train_seat user_order
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrder(long userId,long trId, String st, String ed, Double tot, List<HashMap<String,Object>> psgIds){
        Order order=new Order(trId,st,ed,tot);
        //order
        Long orId=orderDao.insertOrder(order);
        //order_passenger
        for(int i=0,len=psgIds.size();i<len;i++){
            HashMap<String,Object> hs=psgIds.get(i);
            OrderToPassenger orderToPassenger=new OrderToPassenger(orId,Long.parseLong((String)hs.get("psgId")),hs.get("cx")+"-"+hs.get("zw"));
            orderToPassengerDao.insertOrderToPassenger(orderToPassenger);
        }
        //train_seat
        //user_order
        UserToOrder userToOrder=new UserToOrder(userId,orId);
        userToOrderDao.insertUserToOrder(userToOrder);
        return true;
    }
}
