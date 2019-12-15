package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.Train;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface TrainDao {
    public Long selectTrIdByTrNoAndDate(@Param("trNo") String trNo,@Param("date") Date date);
    public String selectTrNoByTrId(long trId);
    public Date selectDateByTrId(long trId);
    public int insertTrain(Train train);
}
