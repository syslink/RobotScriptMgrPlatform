 
package com.pirobot.cmp.service;

import java.util.List;

import com.pirobot.cmp.model.Speaker;
public interface SpeakerService {
    public void save(Speaker speaker);
    public List<Speaker> queryList(String type);
    public void delete(String name);
}
