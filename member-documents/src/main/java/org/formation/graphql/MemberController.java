package org.formation.graphql;

import java.util.Date;
import java.util.List;

import org.formation.model.Document;
import org.formation.model.DocumentRepository;
import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@QueryMapping
	public List<Member> members() {
		return memberRepository.findAll();
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
