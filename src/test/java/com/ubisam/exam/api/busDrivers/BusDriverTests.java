package com.ubisam.exam.api.busDrivers;

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
import com.ubisam.exam.domain.BusDriver;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusDriverTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BusDriverDocs docs;

  @Autowired
  private BusDriverRepository busDriverRepository;

  // Crud 테스트용
  @Test
  void contextLoads() throws Exception{
    // Crud - C
    mvc.perform(post("/api/busDrivers").content(docs::newEntity, "김길동")).andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(uri)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "박길동")).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri)).andExpect(is2xx());
  }

  // 핸들러 테스트용
  @Test
  void contextLoads2 () throws Exception{
    List<BusDriver> result;
    boolean hasResult;

    // 30명의 운전수 추가
    List<BusDriver> busDriverLists = new ArrayList<>();
    for(int i=1; i<=30; i++){
      busDriverLists.add(docs.newEntity(i+"길동", i+"라이센스"));
    }
    busDriverRepository.saveAll(busDriverLists);

    // 운전수 이름 쿼리
    JpaSpecificationBuilder<BusDriver> nameQuery = JpaSpecificationBuilder.of(BusDriver.class);
    nameQuery.where().and().eq("busDriverName", "3길동");
    result = busDriverRepository.findAll(nameQuery.build());
    hasResult = result.stream().anyMatch(u -> "3길동".equals(u.getBusDriverName()));
    assertEquals(true, hasResult);

    // 운전수 라이센스 쿼리
    JpaSpecificationBuilder<BusDriver> licenseQuery = JpaSpecificationBuilder.of(BusDriver.class);
    licenseQuery.where().and().eq("busDriverLicense", "2라이센스");
    result = busDriverRepository.findAll(licenseQuery.build());
    hasResult = result.stream().anyMatch(u -> "2라이센스".equals(u.getBusDriverLicense()));
    assertEquals(true, hasResult);
  }

  // Search 테스트
  @Test
  void contextLoads3() throws Exception{
    // 30명의 운전수 추가
    List<BusDriver> busDriverLists = new ArrayList<>();
    for(int i=1; i<=30; i++){
      busDriverLists.add(docs.newEntity(i+"길동", i+"라이센스"));
    }
    busDriverRepository.saveAll(busDriverLists);

    String uri = "/api/busDrivers/search";
    // Search - 단일 검색 (이름, 라이센스)
    mvc.perform(post(uri).content(docs::setSearchName, "4길동")).andExpect(is2xx());
    mvc.perform(post(uri).content(docs::setSearchLicense, "3라이센스")).andExpect(is2xx());

    // Search - 페이지네이션 6개씩 5페이지
    mvc.perform(post(uri).param("size", "6")).andExpect(is2xx());

    // Search - 정렬 busDriverName,asc
    mvc.perform(post(uri).param("sort", "busDriverName")).andExpect(is2xx());
  }
  
}
