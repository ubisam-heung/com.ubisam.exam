package com.ubisam.exam.api.busRoutes;

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

import com.ubisam.exam.domain.BusDriver;
import com.ubisam.exam.domain.BusRoute;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class BusRouteTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BusRouteDocs docs;

  @Autowired
  private BusRouteRepository busRouteRepository;

  // Crud 테스트용
  @Test
  void contextLoads() throws Exception{
    // Crud - C
    mvc.perform(post("/api/busRoutes").content(docs::newEntity, "가산노선")).andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    // Crud - R
    String uri = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(uri)).andExpect(is2xx());

    // Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(uri).content(docs::updateEntity, body, "강남노선")).andExpect(is2xx());

    // Crud - D
    mvc.perform(delete(uri)).andExpect(is2xx());
  }

  // 핸들러 테스트용
  @Test
  void contextLoads2 () throws Exception{
    
    List<BusRoute> result;
    boolean hasResult;

    // 50개의 노선 추가
    List<BusRoute> busRouteLists = new ArrayList<>();
    for(int i=1; i<=50; i++){
      busRouteLists.add(docs.newEntity(i+"노선", i+"시작", i+"끝"));
    }
    busRouteRepository.saveAll(busRouteLists);

    // 노선 이름 쿼리
    JpaSpecificationBuilder<BusRoute> nameQuery = JpaSpecificationBuilder.of(BusRoute.class);
    nameQuery.where().and().eq("busRouteName", "3노선");
    result = busRouteRepository.findAll(nameQuery.build());
    hasResult = result.stream().anyMatch(u -> "3노선".equals(u.getBusRouteName()));
    assertEquals(true, hasResult);

    // 노선 시작점 쿼리
    JpaSpecificationBuilder<BusRoute> startQuery = JpaSpecificationBuilder.of(BusRoute.class);
    startQuery.where().and().eq("busRouteStart", "1시작");
    result = busRouteRepository.findAll(startQuery.build());
    hasResult = result.stream().anyMatch(u -> "1시작".equals(u.getBusRouteStart()));
    assertEquals(true, hasResult);

    // 노선 종점 쿼리
    JpaSpecificationBuilder<BusRoute> endQuery = JpaSpecificationBuilder.of(BusRoute.class);
    endQuery.where().and().eq("busRouteEnd", "2끝");
    result = busRouteRepository.findAll(endQuery.build());
    hasResult = result.stream().anyMatch(u -> "2끝".equals(u.getBusRouteEnd()));
    assertEquals(true, hasResult);
  }

  // Search 테스트용
  @Test
  void contextLoads3 () throws Exception{
    // 50개의 노선 추가
    List<BusRoute> busRouteLists = new ArrayList<>();
    for(int i=1; i<=50; i++){
      busRouteLists.add(docs.newEntity(i+"노선", i+"시작", i+"끝"));
    }
    busRouteRepository.saveAll(busRouteLists);

    String uri = "/api/busRoutes/search";
    // Search - 단일 검색 (이름, 시작점, 종점)
    mvc.perform(post(uri).content(docs::setSearchName, "1노선")).andExpect(is2xx());
    mvc.perform(post(uri).content(docs::setSearchStart, "2시작")).andExpect(is2xx());
    mvc.perform(post(uri).content(docs::setSearchEnd, "3끝")).andExpect(is2xx());

    // Search - 페이지네이션 10개씩 5페이지
    mvc.perform(post(uri).param("size", "10")).andExpect(is2xx());

    // Search - 정렬 busRouteName,desc
    mvc.perform(post(uri).param("sort", "busRouteName,desc")).andExpect(is2xx());
  }
  
}
