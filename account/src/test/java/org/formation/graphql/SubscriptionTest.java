package org.formation.graphql;

import org.formation.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureGraphQlTester
public class SubscriptionTest {

	@Autowired
	GraphQlTester graphQlTester;
	
	
	@Test
	void subscriptionWithEntityPath() {
				
		Flux<Account> result = this.graphQlTester.query("subscription { accountSubscription {\n"
				+ "    id\n"
				+ "    value\n"
				+ "  } }")
				.executeSubscription()
				.toFlux("accountSubscription", Account.class);

		StepVerifier.create(result)
				.expectNextCount(3)
				.verifyComplete();
	}

	
	
}
