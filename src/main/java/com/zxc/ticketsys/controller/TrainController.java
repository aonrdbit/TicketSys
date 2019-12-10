package com.zxc.ticketsys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.ticketsys.model.Passenger;
import com.zxc.ticketsys.service.TrainService;
import com.zxc.ticketsys.utils.ComUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @RequestMapping(value = "/station/all" ,method = RequestMethod.GET)
    @ResponseBody
    public String queryAllStation() throws JsonProcessingException {
        List list=trainService.queryAllStation();
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        hs.put("list",list);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    public String querySpeTrain(@RequestBody Map<String,Object> para) throws JsonProcessingException, ParseException {
        String st=(String)para.get("st");
        String ed=(String)para.get("ed");
        String date= (String) para.get("date");
        List list=trainService.querySpeTrain(st,ed,ComUtil.strToDate(date));
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("msg","true");
        hs.put("list",list);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}
