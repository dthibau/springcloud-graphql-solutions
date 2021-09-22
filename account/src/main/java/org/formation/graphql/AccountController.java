package org.formation.graphql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.formation.model.Account;
import org.formation.model.AccountCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class AccountController {

	@Autowired
	AccountCrudRepository accountRepository;
	
	@PostConstruct
	public void fillData() {
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account(null, "Bill", 12.3));
		accounts.add(new Account(null, "Mary", 13.3));
		accounts.add(new Account(null, "David", 13.3));
				

		accountRepository.saveAll(Flux.fromIterable(accounts)).subscribe();
	}
	@QueryMapping
	public Flux<Account> accounts() {
		return accountRepository.findAll();
	}
}
