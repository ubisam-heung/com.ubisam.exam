package com.ubisam.exam.api.buses;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Bus;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusDocs extends MockMvcRestDocs{

  // 버스 엔티티 생성용
  public Bus newEntity(String... entity){
    Bus body = new Bus();
    body.setBusNumber(entity.length > 0 ? Integer.valueOf(entity[0]) : super.randomInt());
    body.setBusType(entity.length > 1 ? entity[1] : super.randomText("busType"));
    body.setBusCapacity(super.randomInt());
    body.setBusStatus(super.randomText("busStatus"));
    return body;
  }

  // 버스 번호 수정용
  public Map<String, Object> updateEntity(Map<String, Object> body, String entity){
    body.put("busNumber", entity);
    return body;
  }
  
}
