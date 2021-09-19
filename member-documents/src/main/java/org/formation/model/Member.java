package org.formation.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;



@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})
@Data
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(unique=true)
	private String email;
	
	@NotNull
	private String password;
	
	private String nom,prenom;
	
	private int age;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredDate;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<Document> documents = new HashSet<Document>();

	
	public void addDocument(Document document) {
		this.documents.add(document);
	}
	
	@Transient
	public String getNomComplet() {
		return getPrenom() + " " + getNom();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
