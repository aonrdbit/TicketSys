package com.zxc.ticketsys.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViaDao {
    public List<String> selectAllTrNoFromStToEd(String st,String ed);
}
