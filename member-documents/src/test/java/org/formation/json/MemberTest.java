package org.formation.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.formation.controller.views.MemberViews;
import org.formation.model.Document;
import org.formation.model.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.ApplicationContext;

@JsonTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberTest {

	@Autowired
	ApplicationContext context;
	
	@Autowired
    private JacksonTester<Member> json;

	Member aMember;
	

	@BeforeAll
	public void showContext() {
		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
	}
	@BeforeEach
	public void setUp() {
			
		aMember = new Member();
		aMember.setId(1);
		aMember.setEmail("dd@dd.fr");
		Document doc1 = new Document();
		doc1.setName("Toto");
		aMember.addDocument(doc1);
	}
    @Test
    public void testSerialize() throws Exception {

        assertThat(this.json.forView(MemberViews.List.class).write(aMember))
        	.hasJsonPathStringValue("@.email")
        	.hasEmptyJsonPathValue("@.documents")
        	.extractingJsonPathStringValue("@.email").isEqualTo("dd@dd.fr");
      
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":\"1\",\"email\":\"dd@dd.fr\"}";
        assertThat(this.json.parse(content))
                .isEqualTo(aMember);

        assertThat(this.json.parseObject(content).getEmail()).isEqualTo("dd@dd.fr");
    }
}
