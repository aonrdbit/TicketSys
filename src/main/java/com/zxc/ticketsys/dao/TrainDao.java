package com.zxc.ticketsys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface TrainDao {
    public Long selectTrIdByTrNoAndDate(@Param("trNo") String trNo,@Param("date") Date date);
}
