package com.zxc.ticketsys.dao;

import com.zxc.ticketsys.model.TrainToSeat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainToSeatDao {
    public int selectSeatCntByTrId(@Param("trId") long trId, @Param("lev") int lev);
    public List<TrainToSeat> selectSeatByTrId(@Param("trId") long trId);
    public List<Integer> selectIdxByTrIdAndSeNo(@Param("trId") long trId,
                                                @Param("seNo") String seNo);
    public int updateTrainToSeat(TrainToSeat trainToSeat);
    public int insertTrainToSeat(TrainToSeat trainToSeat);
    public int test(@Param("seNo")String seNo,@Param("seLev")int seLev);
}
