package com.zxc.ticketsys.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zxc.ticketsys.dao.*;
import com.zxc.ticketsys.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private ViaDao viaDao;
    @Autowired
    private TrainToSeatDao trainToSeatDao;
    @Autowired
    private TrainDao trainDao;
    @Autowired
    private PassengerDao passengerDao;
    /**
     * 关联的表：order order_passenger train_seat user_order
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrder(long userId,long trId,String trNo,String st, String ed, Double tot, List<HashMap<String,Object>> psgIds){
        Order order=new Order(trId,st,ed,tot);
        //order
        orderDao.insertOrder(order);
        long orId=order.getOrId();
        //order_passenger
        for(int i=0,len=psgIds.size();i<len;i++){
            HashMap<String,Object> hs=psgIds.get(i);
            OrderToPassenger orderToPassenger=new OrderToPassenger(orId,Long.parseLong(hs.get("psgId").toString()),hs.get("cx")+"-"+hs.get("zw"));
            orderToPassengerDao.insertOrderToPassenger(orderToPassenger);
        }
        //train_seat
        int seIdx=viaDao.selectIdxByTrNoAndSt(trNo,st);
        int edIdx=viaDao.selectIdxByTrNoAndEd(trNo,ed);
        for(int i=0,len=psgIds.size();i<len;i++) {
            HashMap<String,Object> hs=psgIds.get(i);
            for(int j=seIdx;j<=edIdx;j++){
                TrainToSeat trainToSeat=new TrainToSeat(trId,hs.get("cx")+"-"+hs.get("zw"),j,0);
                trainToSeatDao.updateTrainToSeat(trainToSeat);
            }
        }
        //user_order
        UserToOrder userToOrder=new UserToOrder(userId,orId);
        userToOrderDao.insertUserToOrder(userToOrder);
        return true;
    }

    /**
     * 查询订单列表
     * @param userId
     * @return
     */
    public List<HashMap<String,Object>> queryAllOrder(long userId){
        List<Order> list=orderDao.selectAllOrderByUserId(userId);
        int len=list.size();
        List<HashMap<String,Object>> ans=new ArrayList<>();
        for(int i=0;i<len;i++){
            HashMap<String,Object> hs=new HashMap<>();
            Order o=list.get(i);
            Long orId=o.getOrId();
            Long trId=o.getTrId();
            String trNo=trainDao.selectTrNoByTrId(trId);
            String st=o.getStSta();
            hs.put("orId",orId);
            hs.put("trNo",trNo);
            hs.put("st",st);
            hs.put("ed",o.getEdSta());
            hs.put("st_date",trainDao.selectDateByTrId(trId).toString());
            hs.put("st_time",viaDao.selectTimeByTrNoAndSt(trNo,st));
            List<OrderToPassenger> os=orderToPassengerDao.selectPsgByOrId(orId);
            List<HashMap<String,Object>> ss=new ArrayList<>();
            int siz=os.size();
            for(int j=0;j<siz;j++){
                OrderToPassenger orderToPassenger=os.get(j);
                long psgId=orderToPassenger.getPsgId();
                HashMap<String,Object> hss=new HashMap<>();
                Passenger psg=passengerDao.selectPassengerInfoByPsgId(psgId);
                hss.put("name",psg.getName());
                psg.setIdx();
                hss.put("idx",psg.getIdx());
                hss.put("phone",psg.getPhone());
                hss.put("cx",orderToPassenger.getSeNo().substring(0,2));
                hss.put("zw",orderToPassenger.getSeNo().substring(3,6));
                hss.put("psgId",psgId);
                hss.put("status",orderToPassenger.getStatus()==0?"已支付":"已退票");
                ss.add(hss);
            }
            System.out.println(ss);
            hs.put("psgs",ss);
            ans.add(hs);
        }
        return ans;
    }

    /**
     * 退票
     * order_passenger status改为4
     * train_seat 设为1
     * 订单信息不用改
     * @return
     */
    @Transactional
    public boolean delTicket(long orId,long psgId,String seNo,String st,String ed){
        System.out.println(orId+" "+psgId);
        orderToPassengerDao.updateStatusSetFour(orId,psgId);
        long trId=orderDao.selectTrIdByOrId(orId);
        String trNo=trainDao.selectTrNoByTrId(trId);
        int stIdx=viaDao.selectIdxByTrNoAndSt(trNo,st);
        int edIdx=viaDao.selectIdxByTrNoAndEd(trNo,ed);
        for(int i=stIdx;i<=edIdx;i++){
            TrainToSeat trainToSeat=new TrainToSeat(trId,seNo,i,1);
            trainToSeatDao.updateTrainToSeat(trainToSeat);
        }
        return true;
    }

    /**
     * 根据乘客身份证号码查询相关订单
     * @param ID
     * @return
     */
    public List<HashMap<String,Object>> queryAllOrder(String ID){
        long psgIdt=passengerDao.selectPsgIdByID(ID);
        List<Long> orIds=orderToPassengerDao.selectOrIdByPsgId(psgIdt);
        int len=orIds.size();
        List<HashMap<String,Object>> ans=new ArrayList<>();
        for(int i=0;i<len;i++){
            HashMap<String,Object> hs=new HashMap<>();
            Order o=orderDao.selectOrderByOrId(orIds.get(i));
            Long orId=o.getOrId();
            Long trId=o.getTrId();
            String trNo=trainDao.selectTrNoByTrId(trId);
            String st=o.getStSta();
            hs.put("orId",orId);
            hs.put("trNo",trNo);
            hs.put("st",st);
            hs.put("ed",o.getEdSta());
            hs.put("st_date",trainDao.selectDateByTrId(trId).toString());
            hs.put("st_time",viaDao.selectTimeByTrNoAndSt(trNo,st));
            List<OrderToPassenger> os=orderToPassengerDao.selectPsgByOrId(orId);
            List<HashMap<String,Object>> ss=new ArrayList<>();
            int siz=os.size();
            for(int j=0;j<siz;j++){
                OrderToPassenger orderToPassenger=os.get(j);
                long psgId=orderToPassenger.getPsgId();
                HashMap<String,Object> hss=new HashMap<>();
                Passenger psg=passengerDao.selectPassengerInfoByPsgId(psgId);
                hss.put("name",psg.getName());
                psg.setIdx();
                hss.put("idx",psg.getIdx());
                hss.put("phone",psg.getPhone());
                hss.put("cx",orderToPassenger.getSeNo().substring(0,2));
                hss.put("zw",orderToPassenger.getSeNo().substring(3,6));
                hss.put("psgId",psgId);
                hss.put("status",orderToPassenger.getStatus()==0?"已支付":"已退票");
                ss.add(hss);
            }
            hs.put("psgs",ss);
            ans.add(hs);
        }
        return ans;
    }
}
