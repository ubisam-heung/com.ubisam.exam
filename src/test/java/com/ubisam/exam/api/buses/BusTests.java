package com.ubisam.exam.api.buses;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Bus;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BusDocs docs;

  @Autowired
  private BusRepository busRepository;

  // Crud 테스트용
  @Test
  void contextLoads() throws Exception{
    // Crud - C
    mvc.perform(post("/api/buses").content(docs::newEntity, "1140")).andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(get(uri)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "1139")).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri)).andExpect(is2xx());
  }

  // 핸들러 테스트용
  @Test
  void contextLoads2 () throws Exception{
    List<Bus> result;
    boolean hasResult;

    // 40개의 버스 추가
    List<Bus> busLists = new ArrayList<>();
    for(int i=1; i<=40; i++){
      busLists.add(docs.newEntity(i+"140", i+"종류"));
    }
    busRepository.saveAll(busLists);

    // 버스 번호 쿼리
    JpaSpecificationBuilder<Bus> numberQuery = JpaSpecificationBuilder.of(Bus.class);
    numberQuery.where().and().eq("busNumber", 1140);
    result = busRepository.findAll(numberQuery.build());
    hasResult = result.stream().anyMatch(u -> 1140 == u.getBusNumber());
    assertEquals(true, hasResult);

    // 버스 종류 쿼리
    JpaSpecificationBuilder<Bus> typeQuery = JpaSpecificationBuilder.of(Bus.class);
    typeQuery.where().and().eq("busType", "4종류");
    result = busRepository.findAll(typeQuery.build());
    hasResult = result.stream().anyMatch(u -> "4종류".equals(u.getBusType()));
    assertEquals(true, hasResult);
  }

  // Search 테스트용
  @Test
  void contextLoads3 () throws Exception{
    // 40개의 버스 추가
    List<Bus> busLists = new ArrayList<>();
    for(int i=1; i<=40; i++){
      busLists.add(docs.newEntity(i+"140", i+"종류"));
    }
    busRepository.saveAll(busLists);

    // Search - 단일 검색 (버스 번호, 종류)
    mvc.perform(get("/api/buses/search/findByBusNumber").param("busNumber", "4140")).andExpect(is2xx());
    mvc.perform(get("/api/buses/search/findByBusType").param("busType", "3종류")).andExpect(is2xx());

    // Search - 페이지네이션 8개씩 5페이지
    mvc.perform(get("/api/buses").param("size", "6")).andExpect(is2xx());

    // Search - 정렬 busNumber,desc
    mvc.perform(get("/api/buses").param("sort", "busNumber,desc")).andExpect(is2xx());
  }
  
}
