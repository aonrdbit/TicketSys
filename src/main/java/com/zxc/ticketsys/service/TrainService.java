package com.zxc.ticketsys.service;

import com.zxc.ticketsys.dao.StationDao;
import com.zxc.ticketsys.dao.TrainDao;
import com.zxc.ticketsys.dao.TrainToSeatDao;
import com.zxc.ticketsys.dao.ViaDao;
import com.zxc.ticketsys.model.Station;
import com.zxc.ticketsys.model.Train;
import com.zxc.ticketsys.model.TrainToSeat;
import com.zxc.ticketsys.model.Via;
import com.zxc.ticketsys.utils.ComUtil;
import com.zxc.ticketsys.utils.Constant;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 7. 计算价格，先根据车次起点计算第一段价格，同时查询第一段终点，以终点为起点再计算价格，直到终点为ed
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
            int fir=0,sec=0;
            int stIdx=viaDao.selectIdxByTrNoAndSt(trNo,st);
            int edIdx=viaDao.selectIdxByTrNoAndEd(trNo,ed);
            if(stIdx>edIdx){
                continue;
            }
            for(int len=Constant.seNos.size(),j=0;j<len;j++){
                String s=Constant.seNos.get(j);
                TrainToSeat trainToSeat=new TrainToSeat(trId,s,0,1);
                List<Integer> lis=trainToSeatDao.selectIdxByTrIdAndSeNo(trId,s);
                int stt=-1;
                for(int k=0,sz=lis.size();k<sz;k++){
                    if(lis.get(k)==stIdx){
                        stt=k;
                        break;
                    }
                }
                if(stt==-1){
                    trainToSeat.setStatus(0);
                    continue;
                }else{
                    if(lis.get(stt+edIdx-stIdx)!=edIdx){
                        trainToSeat.setStatus(0);
                        continue;
                    }
                }
                if(trainToSeat.getSeNo().substring(0,2).equals("01")){
                    fir++;
                }else{
                    sec++;
                }
            }
            hs.put("fir",fir);
            hs.put("sec",sec);
            double firPrice=viaDao.selectFirPriceByTrNoAndSt(trNo,st);
            double secPrice=viaDao.selectSecPriceByTrNoAndSt(trNo,st);
            String temp_ed=viaDao.selectEdByTrNoAndSt(trNo,st);
            while(!(temp_ed.equals(ed))){
                firPrice+=viaDao.selectFirPriceByTrNoAndSt(trNo,temp_ed);
                secPrice+=viaDao.selectSecPriceByTrNoAndSt(trNo,temp_ed);
                temp_ed=viaDao.selectEdByTrNoAndSt(trNo,temp_ed);
            }
            hs.put("firPrice",firPrice);
            hs.put("secPrice",secPrice);
            list.add(hs);
        }
        return list;
    }

    /**
     * 查询座位信息
     * //慢
     * @param trId
     * @param st
     * @param ed
     * @return
     */
    public List<HashMap<String,Object>> querySpeSeat(Long trId,String st,String ed){
        //车厢座位都是固定的，只需重新设置trId和status(查询这个seNo是否在st到ed之间的status都为1)
        List<TrainToSeat> seats=new ArrayList<>();
        String trNo=trainDao.selectTrNoByTrId(trId);
        int stIdx=viaDao.selectIdxByTrNoAndSt(trNo,st);
        int edIdx=viaDao.selectIdxByTrNoAndEd(trNo,ed);
        //查该trId的每个座位status为1是哪些段
        for(int len=Constant.seNos.size(),i=0;i<len;i++){
            String s=Constant.seNos.get(i);
            TrainToSeat trainToSeat=new TrainToSeat(trId,s,0,1);
            List<Integer> list=trainToSeatDao.selectIdxByTrIdAndSeNo(trId,s);
            int stt=-1;
            for(int j=0,siz=list.size();j<siz;j++){
                if(list.get(j)==stIdx){
                    stt=j;
                    break;
                }
            }
            if(stt==-1){
                trainToSeat.setStatus(0);
            }else{
                if(list.get(stt+edIdx-stIdx)!=edIdx){
                    trainToSeat.setStatus(0);
                }
            }
            seats.add(trainToSeat);
        }
        ArrayList<HashMap<String,Object>> list=new ArrayList<>();
        for(int i=1;i<=3;i++){
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("cx",i);
            ArrayList<HashMap<String,Object>> son=new ArrayList<>();
            for(int j=1;j<=Constant.sz;j++){
                HashMap<String,Object> ss=new HashMap<>();
                TrainToSeat trainToSeat=seats.get((i-1)*Constant.sz+j-1);
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

    /**
     * 添加车次
     * train train_seat via
     * @param hs
     * @return
     */
    @Transactional
    public boolean addTrain(HashMap<String,Object> hs){
        System.out.println(hs);
        String trNo=(String)hs.get("trNo");
        List<HashMap<String,Object>> ls= (List<HashMap<String, Object>>) hs.get("list");
        int idx=ls.size();
        Train train=new Train();
        for(Date d: Constant.dates){
            train.setTrNo(trNo);
            train.setTrDate(d);
            System.out.println("tarin"+train);
            trainDao.insertTrain(train);
            long trId=train.getTrId();
            TrainToSeat trainToSeat=new TrainToSeat();
            trainToSeat.setStatus(1);
            trainToSeat.setTrId(trId);
            for(int j=0,len=Constant.seNos.size();j<len;j++){
                for(int k=1;k<=idx;k++){
                    trainToSeat.setSeNo(Constant.seNos.get(j));
                    trainToSeat.setIdx(k);
                    System.out.println("trainToSeat"+trainToSeat);
                    trainToSeatDao.insertTrainToSeat(trainToSeat);
                }
            }
        }
        for(int i=0;i<idx;i++){
            HashMap<String,Object> h=ls.get(i);
            String stationA= (String) h.get("stationA");
            String stationB= (String) h.get("stationB");
            int id= (int) h.get("idx");
            String staTime=(String)h.get("staTime");
            String arrTime=(String)h.get("arrTime");
            Time sta=Time.valueOf(staTime+":00");
            Time arr=Time.valueOf(arrTime+":00");
            int dwellTime=Integer.parseInt((String)h.get("dwellTime"));
            double firPrice=Double.parseDouble((String)h.get("firPrice"));
            double secPrice=Double.parseDouble((String)h.get("secPrice"));
            Via via=new Via(trNo,stationA,stationB,id,sta,arr,dwellTime,firPrice,secPrice);
            viaDao.insertVia(via);
            System.out.println("via"+via);
        }
        return true;
    }

    public List<Via> queryTrainInfo(String trNo){
        return viaDao.selectViaByTrNo(trNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean modTrainInfo(String trNo,List<HashMap<String,Object>> list){
        viaDao.deleteViaByTrNo(trNo);
        for(HashMap<String,Object> h:list){
            String stationA= (String) h.get("stationA");
            String stationB= (String) h.get("stationB");
            int id= (int) h.get("idx");
            String staTime=(String)h.get("staTime");
            String arrTime=(String)h.get("arrTime");
            Time sta=Time.valueOf(staTime);
            Time arr=Time.valueOf(arrTime);
            int dwellTime=Integer.parseInt((String)h.get("dwellTime"));
            double firPrice=Integer.parseInt(h.get("firPrice").toString())*1.0;
            double secPrice=Integer.parseInt(h.get("secPrice").toString())*1.0;
            Via via=new Via(trNo,stationA,stationB,id,sta,arr,dwellTime,firPrice,secPrice);
            viaDao.insertVia(via);
        }
        return true;
    }
}
