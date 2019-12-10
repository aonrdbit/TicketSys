package com.zxc.ticketsys.dao;

import org.apache.ibatis.annotations.Mapper;

import java.sql.Time;
import java.util.List;

@Mapper
public interface ViaDao {
    public List<String> selectAllTrNoFromStToEd(String st,String ed);
    public Time selectTimeByTrNoAndEd(String trNo,String ed);
    public Time selectTimeByTrNoAndSt(String trNo,String st);
}
