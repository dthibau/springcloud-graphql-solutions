package org.formation;

import java.util.ArrayList;
import java.util.List;

import org.formation.model.Account;
import org.formation.model.AccountCrudRepository;
import org.formation.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class AccountApplicationTests {

	@Autowired
	AccountCrudRepository accountCrudRepository;
	
	@Autowired
	AccountService accountService;
	

	@Test
	public void playWithRepositoryBlocking() {
		accountCrudRepository.deleteAll().block();

		// save a couple of customers
		accountCrudRepository.save(new Account(null, "Bill", 12.3)).block();
		accountCrudRepository.save(new Account(null, "Mary", 13.3)).block();
		
		accountCrudRepository.saveAll(Flux.just(new Account(null, "David", 13.3))).blockLast();

		System.out.println("Playng with Repository Blocking");
		System.out.println("----------------------");
		// fetch all accounts
		accountCrudRepository.findAll().subscribe(a -> {
			System.out.println("findAll():" + a);
			}
		);
			
		// fetch all byValue
		accountCrudRepository.findAllByValue(12.3).subscribe(a -> {
			System.out.println("findByValue():" + a);
		});
			
		// fetch an individual customer
		accountCrudRepository.findFirstByOwner(Mono.just("Bill")).subscribe(a -> {
			System.out.println("findFirstByOwner('Bill'):" + a);
		});

		accountCrudRepository.deleteAll();
		
	}

	@Test
	public void playWithRepositoryUnBlocking() {
		accountCrudRepository.deleteAll().block();
		
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account(null, "Bill", 12.3));
		accounts.add(new Account(null, "Mary", 13.3));
		accounts.add(new Account(null, "David", 13.3));
				

		// save a couple of customers
	
		
		accountCrudRepository.saveAll(Flux.fromIterable(accounts)).subscribe();

		System.out.println("Playing with Repository Unblocking");
		System.out.println("----------------------");
		// fetch all accounts
		accountCrudRepository.findAll().subscribe(a -> {
			System.out.println("findAll():" + a);
			}
		);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
		}
		accountCrudRepository.findAll().subscribe(a -> {
			System.out.println("findAll2():" + a);
			}
		);	
			
		// fetch all byValue
		accountCrudRepository.findAllByValue(12.3).subscribe(a -> {
			System.out.println("findByValue():" + a);
		});
			
		// fetch an individual customer
		accountCrudRepository.findFirstByOwner(Mono.just("Bill")).subscribe(a -> {
			System.out.println("findFirstByOwner('Bill'):" + a);
		});

		accountCrudRepository.deleteAll();
		
	}

	@Test
	public void playWithTemplate() {
		accountService.save(Mono.just(new Account(null, "Bill", 12.3))).block();
		accountService.save(Mono.just(new Account(null, "Mary", 13.3))).block();

		System.out.println("Playng with Template");
		System.out.println("----------------------");
		// fetch all accounts
		accountService.findAll().subscribe(a -> {
			System.out.println("findAll():" + a);
			}
		);
			
	

		
	}


}
