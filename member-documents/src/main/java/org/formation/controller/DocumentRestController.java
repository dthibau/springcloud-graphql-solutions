package org.formation.controller;

import java.util.List;

import javax.validation.Valid;

import org.formation.model.Document;
import org.formation.model.DocumentRepository;
import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestController {


	private final MemberRepository memberRepository;
	private final DocumentRepository documentRepository;

	public DocumentRestController(DocumentRepository documentRepository, MemberRepository memberRepository) {
		this.documentRepository = documentRepository;
		this.memberRepository = memberRepository;

	}

	/**
	 * @param owner
	 * @return
	 * @throws MemberNotFoundException 
	 */
	@GetMapping("/owner/{id}")
	public List<Document> getDocuments(@PathVariable("id") Long id) throws MemberNotFoundException {

		Member member = memberRepository.findById(id).orElseThrow(
				() -> new MemberNotFoundException("Id " + id));

		return documentRepository.findByOwner(member);

		
	}
	
	@PostMapping("/owner/{id}")
	public ResponseEntity<Void> addDocument(@PathVariable("id") Long id, @Valid Document document) throws MemberNotFoundException {
		Member member = memberRepository.findById(id).orElseThrow(
				() -> new MemberNotFoundException("Id " + id));
		
		member.addDocument(document);
		memberRepository.save(member);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		
	}

}
