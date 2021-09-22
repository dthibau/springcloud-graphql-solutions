package org.formation.graphql;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class GraphQLTest {

	@Autowired
	MemberRepository memberRepository;
	
	DataFetcher<List<Member>> membersFetcher;
	
	GraphQL graphQL;
	
	@BeforeEach
	public void init() throws IOException {
		
		_initFetchers();
		
		File schema = new ClassPathResource("graphql/member.graphqls").getFile();
		SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);
        
        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("members", membersFetcher))
                .build();

		SchemaGenerator schemaGenerator = new SchemaGenerator();

		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		
		graphQL = GraphQL.newGraphQL(graphQLSchema).build();

	}
	
	@Test
	public void testMemberNames() {
		ExecutionInput executionInput = ExecutionInput.newExecutionInput()
	             .query("query { members { nom } }")
	                .build();
		
		ExecutionResult executionResult = graphQL.execute(executionInput);
		
		Object data = executionResult.getData();
		Assertions.assertNotNull(executionResult.getErrors().isEmpty());
		Assertions.assertTrue(executionResult.getErrors().isEmpty());
		
		System.out.println(data);
		
	}
	
	private void _initFetchers() {
		membersFetcher = new DataFetcher<List<Member>>() {
            @Override
            public List<Member> get(DataFetchingEnvironment environment) {
                return memberRepository.findAll();
            }
        };
	}
}
