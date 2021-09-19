package org.formation;

import org.formation.model.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Tp3ApplicationTests {
	
	@Autowired
	DocumentRepository documentRepository;
	

	@Test
	void contextLoads() {
		

	}

	
}
