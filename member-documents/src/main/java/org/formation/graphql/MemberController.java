package org.formation.graphql;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.formation.model.AccountDto;
import org.formation.model.Document;
import org.formation.model.DocumentRepository;
import org.formation.model.Member;
import org.formation.model.MemberAccountDto;
import org.formation.model.MemberRepository;
import org.formation.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class MemberController {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired 
	AccountService accountService;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@QueryMapping
	public List<Member> members() {
		return memberRepository.findAll();
	}
	
	@QueryMapping
	public MemberAccountDto memberWithAccount(@Argument Long id) throws JsonMappingException, JsonProcessingException {
		Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		AccountDto accountDto = accountService.getAccountByOwner(member.getEmail());
		return new MemberAccountDto(member,accountDto);
	}
	
	@SchemaMapping
	public List<Document> documents(Member member) {
		return documentRepository.findByOwner(member);
		
	}
	
	@MutationMapping
	public Member addMember(@Argument MemberInput memberInput) {
		
		return memberRepository.save(Member.builder()
				.nom(memberInput.getNom())
				.prenom(memberInput.getPrenom())
				.email(memberInput.getEmail())
				.password(memberInput.getPassword())
				.age(memberInput.getAge())
				.registeredDate(new Date())
				.build());
	}
}
