package org.formation.model;

import lombok.Data;

@Data
public class MemberAccountDto {

	private Long id;
	private String nom;
	private String prenom;
	private String email;
	private Double value;
	public MemberAccountDto(Member member, AccountDto accountDto) {
		this.id = member.getId();
		this.nom = member.getNom();
		this.prenom = member.getPrenom();
		this.email = member.getEmail();
		this.value = accountDto != null ? accountDto.getValue() : null;
	}
}
