package com.zxc.ticketsys.service;

import com.zxc.ticketsys.dao.StationDao;
import com.zxc.ticketsys.dao.TrainDao;
import com.zxc.ticketsys.dao.TrainToSeatDao;
import com.zxc.ticketsys.dao.ViaDao;
import com.zxc.ticketsys.model.Station;
import com.zxc.ticketsys.model.TrainToSeat;
import com.zxc.ticketsys.utils.ComUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.Collator;
import java.util.*;

@Service
public class TrainService {
    @Autowired
    private StationDao stationDao;
    @Autowired
    private ViaDao viaDao;
    @Autowired
    private TrainDao trainDao;
    @Autowired
    private TrainToSeatDao trainToSeatDao;

    /**
     * 查询所有车站信息，按拼音排序
     * @return
     */
    public List<HashMap<String,Object>> queryAllStation(){
        List<HashMap<String,Object>> ans=new ArrayList<>();
        List<Station> list=stationDao.selectAllStation();
        Collections.sort(list, new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
                return com.compare(o1.getStaName(), o2.getStaName());
            }
        });
        for(int i=0;i<list.size();i++){
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("value",i+1);
            hs.put("label",list.get(i).getStaName());
            ans.add(hs);
        }
        return ans;
    }

    /**
     * 查询车次信息
     * 1. 先查via 对应st和ed有哪些车次
     * 2. 用车次和日期查到trId，方便后面操作
     * 3. 查ed的到达时间
     * 4. 查st的出发时间
     * 5. 计算历时
     * 6. 根据trId查train_seat看有没有座位
     * @param st
     * @param ed
     * @param date
     * @return
     */
    public List<HashMap<String,Object>> querySpeTrain(String st,String ed,Date date){
        List<String> trNos=viaDao.selectAllTrNoFromStToEd(st,ed);
        List<HashMap<String,Object>> list=new ArrayList<>();
        int siz=trNos.size();
        for(int i=0;i<siz;i++){
            String trNo=trNos.get(i);
            long trId=trainDao.selectTrIdByTrNoAndDate(trNo,date);
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("trNo",trNos.get(i));
            hs.put("trId",trId);
            hs.put("st",st);
            hs.put("ed",ed);
            Time st_time=viaDao.selectTimeByTrNoAndSt(trNo,st);
            Time ed_time=viaDao.selectTimeByTrNoAndEd(trNo,ed);
            hs.put("st_time",st_time);
            hs.put("ed_time",ed_time);
            hs.put("dur", ComUtil.diffHour(st_time,ed_time));
            hs.put("fir",trainToSeatDao.selectSeatCntByTrId(trId,1));
            hs.put("sec",trainToSeatDao.selectSeatCntByTrId(trId,2));
            list.add(hs);
        }
        return list;
    }

    /**
     * 查询座位信息
     * @param trId
     * @return
     */
    public List<HashMap<String,Object>> querySpeSeat(Long trId){
        List<TrainToSeat> seats=trainToSeatDao.selectSeatByTrId(trId);
        ArrayList<HashMap<String,Object>> list=new ArrayList<>();
        for(int i=1;i<=4;i++){
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("cx",i);
            ArrayList<HashMap<String,Object>> son=new ArrayList<>();
            for(int j=1;j<=80;j++){
                HashMap<String,Object> ss=new HashMap<>();
                TrainToSeat trainToSeat=seats.get((i-1)*80+j-1);
                ss.put("seNo",trainToSeat.getSeNo());
                ss.put("status",trainToSeat.getStatus()==0);
                ss.put("check",false);
                son.add(ss);
            }
            hs.put("ss",son);
            list.add(hs);
        }
        return list;
    }

}
