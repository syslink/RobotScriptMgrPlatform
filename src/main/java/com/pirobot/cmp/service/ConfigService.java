/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.service;

import java.util.List;
import com.pirobot.cmp.model.Config;
public  interface ConfigService
{

  public  Config queryById(String sequenceId);
  public  void save(Config paramConfig);
  public  List<Config>  queryConfigByDomain(String domain);
  public void delete(String sequenceId) ;
  public  void update(Config config); 
  public List<String> queryDomainList();
  public  Config querySingle(Config config);
  public  List<Config>   queryList(Config config);
}