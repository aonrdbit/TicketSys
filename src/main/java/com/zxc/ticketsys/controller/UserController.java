package com.zxc.ticketsys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController{
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestHeader Map<String,Object> he,@RequestBody Map<String,Object> para) throws JsonProcessingException {
        System.out.println(he);
        String username=(String)para.get("username");
        String password=(String)para.get("password");
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("token","token"+username+password);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test(@RequestHeader Map<String,Object> he) throws JsonProcessingException {
        System.out.println(he);
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}
