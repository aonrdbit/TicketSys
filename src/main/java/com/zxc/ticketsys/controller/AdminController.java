package com.zxc.ticketsys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.ticketsys.model.Via;
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
        HashMap<String,Object> par= (HashMap<String, Object>) para.get("train");
        trainService.addTrain(par);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping("/train/query")
    public String queryTrain(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        String trNo=(String)para.get("trNo");
        //查出每一段的信息 渲染到form里直接修改
        List<Via> list=trainService.queryTrainInfo(trNo);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        hs.put("list",list);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping("/train/mod")
    public String modTrain(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        String trNo=(String)para.get("trNo");
        List<HashMap<String,Object>> list=(List<HashMap<String, Object>>)para.get("list");
        System.out.println(trNo);
        System.out.println(list);
        //先删除via表的trNo，再插入新的via记录
        boolean check=trainService.modTrainInfo(trNo,list);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg",check+"");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}
