package org.formation.graphql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.formation.model.Account;
import org.formation.model.AccountCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class AccountController {

	@Autowired
	AccountCrudRepository accountRepository;
	
	@PostConstruct
	public void fillData() {
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account(null, "dthibau@wmmod.com", 12.3));
		accounts.add(new Account(null, "dthibau@mymeetingsondemand.com", 13.3));
		accounts.add(new Account(null, "david.thibau@gmail.com", 13.3));
				

		accountRepository.saveAll(Flux.fromIterable(accounts)).subscribe();
	}
	@QueryMapping
	public Flux<Account> accounts() {
		return accountRepository.findAll();
	}
	
	@QueryMapping
	public Mono<Account> accountByOwner(@Argument String owner) {
		accountRepository.findFirstByOwner(Mono.just(owner)).subscribe(System.out::println);
		return accountRepository.findFirstByOwner(Mono.just(owner));
	}
	
	@SubscriptionMapping
	public Flux<Account> accountSubscription() {
		return accountRepository.findAll();
//		List<Account> accounts = new ArrayList<>();
//		accounts.add(new Account("1", "Bill", 12.3));
//		accounts.add(new Account("2", "Mary", 13.3));
//		accounts.add(new Account("3", "David", 13.3));
//		
//		return Flux.fromIterable(accounts);
                		
	}
	
}
