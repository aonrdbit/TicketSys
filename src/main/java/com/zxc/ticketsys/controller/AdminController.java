package com.zxc.ticketsys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.ticketsys.service.OrderService;
import com.zxc.ticketsys.service.TrainService;
import com.zxc.ticketsys.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TrainService trainService;

    @RequestMapping("/order/all")
    public String queryAllOrder(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        String ID=(String)para.get("ID");
        List list=orderService.queryAllOrder(ID);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        hs.put("list",list);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping("/train/add")
    public String addTrain(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        System.out.println(Constant.dates);
        HashMap<String,Object> par= (HashMap<String, Object>) para.get("train");
        trainService.addTrain(par);
        System.out.println(par);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}
