package com.ubisam.exam.api.addressGroups;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class AddressGroupDocs extends MockMvcRestDocs{
  
  public Map<String, Object> newEntity(String name){
    Map<String, Object> addressGroup = new HashMap<>();
    addressGroup.put("name", name);
    return addressGroup;
  }

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

  public Map<String, Object> setKeyword(String keyword){
    Map<String, Object> addressGroup = new HashMap<>();
    addressGroup.put("keyword", keyword);
    return addressGroup;
  }

}

