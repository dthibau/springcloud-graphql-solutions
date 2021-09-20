package org.formation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MemberDocumentsApplicationTests {
	
	@Autowired
	MockMvc mvc;

	@Test
	void contextLoads() {
		
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testFullLoadMember() throws Exception {
		ResultActions result = mvc.perform(get("/api/members/1"));
		MvcResult mvcResult = result.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());

		mvc.perform(get("/api/members/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nom").value("THIBAU"));
	}
	
}
