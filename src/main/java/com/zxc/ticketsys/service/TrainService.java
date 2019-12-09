package com.zxc.ticketsys.service;

import com.zxc.ticketsys.dao.StationDao;
import com.zxc.ticketsys.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;

@Service
public class TrainService {
    @Autowired
    private StationDao stationDao;

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
}
