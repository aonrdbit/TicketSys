package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.Via;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Property;

import java.sql.Time;
import java.util.List;

@Mapper
public interface ViaDao {
    public List<String> selectAllTrNoFromStToEd(String st,String ed);
    public Time selectTimeByTrNoAndEd(String trNo,String ed);
    public Time selectTimeByTrNoAndSt(String trNo,String st);
    public String selectEdByTrNoAndSt(String trNo,String st);
    public Double selectFirPriceByTrNoAndSt(String trNo,String st);
    public Double selectSecPriceByTrNoAndSt(String trNo,String st);
    public Integer selectIdxByTrNoAndSt(String trNo,String st);
    public Integer selectIdxByTrNoAndEd(String trNo,String ed);
    public int insertVia(Via via);
    public List<Via> selectViaByTrNo(String trNo);
    public Integer deleteViaByTrNo(String trNo);
}
