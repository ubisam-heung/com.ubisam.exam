package com.ubisam.exam.api.addressGroups;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.api.addresses.AddressDocs;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressGroupTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AddressGroupDocs docs;
  
  @Test
  public void contextLoads() throws Exception {
    //Crud - C
    mockMvc
      .perform(post("/api/addressGroups")
      .content(docs::newEntity, "연구소"))
      .andDo(print())
      .andExpect(is2xx())
    ;

    //Crud - R (전체)
    mockMvc
      .perform(post("/api/addressGroups/search")
      .content(docs::setKeyword, ""))
      .andDo(print())
      .andExpect(is2xx())
    ;

    //Crud - R (단건)
    mockMvc
      .perform(post("/api/addressGroups/search")
      .content(docs::setKeyword, "연구소"))
      .andDo(print())
      .andExpect(is2xx())
      .andDo(result(docs::context , "data_body"))
    ;

    //Crud - U
    Map<String, Object> entity = docs.context("data_body", "$");
    String url = docs.context("data_body", "$._embedded.addressGroups[0]._links.self.href");
    System.out.println("url : " + url);

    mockMvc
      .perform(put(url)
      .content(docs::updateEntity, entity, "홍길동111"))
      .andExpect(is2xx())
      .andDo(print())
      .andExpect(isJson("$.name", "홍길동111"))
    ;

    //Crud - D
    mockMvc
      .perform(delete(url))
      .andExpect(is2xx())
      .andDo(print());

    //Crud - R (삭제 후 단건)
    mockMvc
      .perform(post("/api/addressGroups/search")
      .content(docs::setKeyword, "연구소"))
      .andDo(print())
      .andExpect(is2xx())
    ;
  }
  
}
