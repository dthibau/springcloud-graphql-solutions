package org.formation.service;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.formation.model.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AccountService {

	RestTemplate restTemplate;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	Random r = new Random();
	
	ObjectMapper mapper = new ObjectMapper();
	
    String q ="{\"query\":\"{\\n  accountByOwner(owner: \\\"dthibau@wmmod.com\\\") {\\n    id\\n    value\\n  }\\n}\",\"variables\":null}";

	
	public AccountService(RestTemplateBuilder builder) {
		restTemplate = builder.build();
	}
	
	public AccountDto getAccountByOwner(String owner) throws JsonMappingException, JsonProcessingException {
		
		List<ServiceInstance> app = discoveryClient.getInstances("ACCOUNT-SERVICE"); // (2)
	      ServiceInstance si = app.get(r.nextInt(app.size()));
	      
		  HttpHeaders headers = new HttpHeaders();
		    headers.add("content-type", "application/json"); 

		    String url = si.getUri() + "/graphql";
		    System.out.println(url);
		    
		    ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(q, headers), String.class);
		    System.out.println("The response================="+response.getBody());
		    
		    // Decode response
		    Map<String, Object> map = mapper.readValue(response.getBody(), Map.class);
		    
		    AccountDto dto = new AccountDto();
		    Map<String,Object> dataMap = (Map)map.get("data");
		    Map<String,Object> accountByOwnerMap = (Map)dataMap.get("accountByOwner");
		    dto.setId(accountByOwnerMap.get("id").toString());
		    dto.setValue((Double)accountByOwnerMap.get("value"));
		    
		    return dto;

	}
}
