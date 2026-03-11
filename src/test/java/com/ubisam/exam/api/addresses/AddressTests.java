package com.ubisam.exam.api.addresses;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.isJson;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressTests {

    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private AddressDocs docs;

    @Test
    public void contextLoads() throws Exception {

        //CRUD - Create
        mockMvc
            .perform(post("/api/addresses").content(docs::newEntity, "홍길동"))
            .andExpect(is2xx())
            .andDo(print())
            // .andDo(print())
            // .andExpect(isJson("$.name", "test1"))
            // 응답 받은 json을 docs에 저장
            .andDo(result(docs::context , "a"))
        ;

        String name = docs.context("a", "$.name");
        System.out.println(name);

        String url = docs.context("a", "$._links.self.href");

        //CRUD - Read
        mockMvc.perform(get(url)).andExpect(is2xx()).andDo(print());

        //CRUD - Update
        Map<String, Object> entity = docs.context("a", "$");

        mockMvc
            .perform(put(url).content(docs::updateEntity, entity, "홍길동111"))
            .andExpect(is2xx())
            .andDo(print())
            .andExpect(isJson("$.name", "홍길동111"))
        ;
    }

}
