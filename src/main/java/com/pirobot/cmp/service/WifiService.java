 
package com.pirobot.cmp.service;

import java.util.List;

import com.pirobot.cmp.model.Wifi;
public interface WifiService {

    public void save(List<Wifi> list);
    public List<Wifi> get(String devid);
   
}
