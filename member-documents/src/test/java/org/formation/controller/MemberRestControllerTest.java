package org.formation.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(value=MemberRestController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberRestControllerTest {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private MemberRepository memberRepository;
	
	
	Member aMember;
	
	@BeforeAll
	public void showContext() {
		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

	}

	@BeforeEach
	public void setUp() {
		aMember = new Member();
		aMember.setId(1);
		aMember.setNom("David");
		
	}

	@Test
	@WithMockUser(roles = "USER")
	public void testFullLoadMember() throws Exception {
		given(this.memberRepository.fullLoad(1l)).willReturn(aMember);
		ResultActions result = mvc.perform(get("/api/members/1"));
		MvcResult mvcResult = result.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());

		mvc.perform(get("/api/members/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nom").value("David"));
	}
	
	@Test
	@WithAnonymousUser
	public void testAclGetMember() throws Exception {
		given(this.memberRepository.fullLoad(1l)).willReturn(aMember);

		mvc.perform(get("/api/members/1")).andExpect(status().isUnauthorized());
	}
}
