package com.zxc.ticketsys.service;

import com.zxc.ticketsys.dao.PassengerDao;
import com.zxc.ticketsys.dao.UserDao;
import com.zxc.ticketsys.dao.UserToPassengerDao;
import com.zxc.ticketsys.model.Passenger;
import com.zxc.ticketsys.model.User;
import com.zxc.ticketsys.model.UserToPassenger;
import com.zxc.ticketsys.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PassengerDao passengerDao;
    @Autowired
    private UserToPassengerDao userToPassengerDao;

    /**
     * 检查密码
     * @param userName
     * @param PassWord
     * @return
     */
    public boolean checkUserPassWord(String userName,String PassWord){
        PassWord= MD5.encodeByMD5(PassWord);
        String pass=userDao.selectPassWordByUserName(userName);
        return pass!=null && pass.equals(PassWord);
    }

    /**
     * 获取用户信息(userId)
     * @param username
     * @return
     */
    public User getUserInfo(String username){
        return userDao.selectUserByUserName(username);
    }

    /**
     * 注册用户
     * 加入用户表，如果乘客表中没有该用户，加入乘客表
     * @param username
     * @param password
     * @param name
     * @param ID
     * @param phone
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean registerUser(String username,String password,String name,String ID,String phone){
        password=MD5.encodeByMD5(password);
        userDao.insertUser(new User(username,password));
        int x=passengerDao.selectCountID(ID);
        if(x==0){
            passengerDao.insertPassenger(new Passenger(name, ID, phone));
        }
        return true;
    }


    /**
     * 查询用户关联的所有乘客
     * @param userId
     * @return
     */
    public List<Passenger> queryAllPassenger(long userId){
        List<Long> psgIds=userToPassengerDao.selectPassengerByUserId(userId);
        List<Passenger> passengers=new ArrayList<>();
        for(int i=0;i<psgIds.size();i++){
            Passenger passenger=passengerDao.selectPassengerInfoByPsgId(psgIds.get(i));
            passenger.setIdx();
            passengers.add(passenger);
        }
        return passengers;
    }


    /**
     * 添加用户的常用联系人（乘客）
     * 先根据身份证查询乘客是否已存在
     * @param userId
     * @param Name
     * @param ID
     * @param phone
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addPassenger(long userId,String Name,String ID,String phone){
        Passenger passenger=new Passenger(Name,ID,phone);
        passenger.setIdx();
        int cnt=passengerDao.selectCountID(ID);
        if(cnt==0){
            passengerDao.insertPassenger(passenger);
        }
        long psgId=passengerDao.selectPsgIdByID(ID);
        List<Long> psgIds=userToPassengerDao.selectPassengerByUserId(userId);
        if(!psgIds.contains(psgId)){
            UserToPassenger userToPassenger=new UserToPassenger(userId,psgId);
            userToPassengerDao.insertUserToPassenger(userToPassenger);
            return true;
        }
        return false;
    }
}
