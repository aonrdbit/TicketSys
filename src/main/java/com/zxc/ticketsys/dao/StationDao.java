package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.Station;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StationDao {
    public List<Station> selectAllStation();
}
