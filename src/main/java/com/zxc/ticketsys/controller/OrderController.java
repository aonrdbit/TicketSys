package com.zxc.ticketsys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.ticketsys.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/add")
    public String addOrder(@RequestBody Map<String,Object> para)throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        long userId=Long.parseLong((String)para.get("userId"));
        long trId=Long.parseLong((String)para.get("trId"));
        String trNo=(String)para.get("trNo");
        String st=(String)para.get("st");
        String ed=(String)para.get("ed");
        double firPrice=Double.parseDouble((String)para.get("firPrice"));
        double secPrice=Double.parseDouble((String)para.get("secPrice"));
        double tot=Double.parseDouble((String)para.get("tot"));
        String date=(String)para.get("date");
        ArrayList<HashMap<String,Object>> list=(ArrayList<HashMap<String,Object>>)para.get("psgs");
        System.out.println(trId+" "+trNo+" "+st+" "+ed+" "+firPrice+" "+secPrice+" "+date);
//        boolean check=orderService.addOrder(userId,trId,st,ed,tot,list);
        hs.put("msg","true");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}