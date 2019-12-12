package com.zxc.ticketsys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.ticketsys.model.Passenger;
import com.zxc.ticketsys.model.User;
import com.zxc.ticketsys.service.UserService;
import com.zxc.ticketsys.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        String username=(String)para.get("username");
        String password=(String)para.get("password");
        HashMap<String,Object> hs=new HashMap<>();
        boolean check=userService.checkUserPassWord(username,password);
        String token="";
        long userId=-1;
        if(check){
            token= TokenUtil.sign(new User(username,password));
            userId=userService.getUserInfo(username).getUserId();
            hs.put("msg","true");
        }else{
            hs.put("msg","false");
        }
        hs.put("userId",userId);
        hs.put("token",token);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        String username=(String)para.get("username");
        String password=(String)para.get("password");
        String name=(String)para.get("name");
        String ID=(String)para.get("ID");
        String phone=(String)para.get("phone");
        HashMap<String,Object> hs=new HashMap<>();
        boolean check=userService.registerUser(username,password,name,ID,phone);
        if(check){
            hs.put("msg","true");
        }else{
            hs.put("msg","false");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/passenger/all",method = RequestMethod.POST)
    @ResponseBody
    public String queryAllPassenger(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        long userId=Integer.parseInt((String)para.get("userId"));
        List<Passenger> list=userService.queryAllPassenger(userId);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        hs.put("list",list);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/passenger/add",method = RequestMethod.POST)
    @ResponseBody
    public String addPassenger(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        long userId=Integer.parseInt((String)para.get("userId"));
        String name=(String)para.get("Name");
        String ID=(String)para.get("ID");
        String phone=(String)para.get("phone");
        boolean check=userService.addPassenger(userId,name,ID,phone);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg",check+"");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/passenger/del",method = RequestMethod.POST)
    @ResponseBody
    public String delPassenger(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        long userId=Integer.parseInt((String)para.get("userId"));
        long psgId=Integer.parseInt((String)para.get("psgId"));
        boolean check=userService.delPassenger(userId,psgId);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg",check+"");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}
