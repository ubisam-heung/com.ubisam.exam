package com.ubisam.exam.api.addresses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class AddressDocs extends MockMvcRestDocs {
    
    //json 을 만들기 위한 과정
    public Map<String, Object> newEntity(String name) {

        Map<String, Object> address;
        address = new HashMap<>();
        address.put("name", name);
        address.put("phone", super.randomInt());
        address.put("address", super.randomText("address"));
        // Address address = new Address();
        // address.setName("test1");
        // address.setPhone("010-1234-5678");
        // address.setAddress("Seoul");

        return address;
    }

    public Map<String, Object> updateEntity(Map<String, Object> entity, String name) {

        entity.put("name", name);
        return entity;
    }
}
