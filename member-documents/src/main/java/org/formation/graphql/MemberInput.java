package org.formation.graphql;

import lombok.Data;

@Data
public class MemberInput {

	private String nom,prenom,email,password;
	
	private int age;
}
