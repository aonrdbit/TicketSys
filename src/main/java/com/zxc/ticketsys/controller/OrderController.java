package com.zxc.ticketsys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.ticketsys.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        boolean check=orderService.addOrder(userId,trId,trNo,st,ed,tot,list);
        hs.put("msg",check+"");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping("/all")
    @ResponseBody
    public String queryALlOrder(@RequestBody Map<String,Object> para)throws JsonProcessingException {
        long userId=Long.parseLong((String)para.get("userId"));
        List<HashMap<String,Object>> list=orderService.queryAllOrder(userId);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        hs.put("list",list);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping("/del")
    public String delTicket(@RequestBody Map<String,Object> para)throws JsonProcessingException{
        long orId=Long.parseLong((String)para.get("orId"));
        long psgId=Long.parseLong((String)para.get("psgId"));
        String seNo=(String)para.get("seNo");
        String st=(String)para.get("st");
        String ed=(String)para.get("ed");
        orderService.delTicket(orId,psgId,seNo,st,ed);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

}
