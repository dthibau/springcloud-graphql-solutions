package org.formation.controller;

import java.util.Date;

public class ErrorDto {

	private final String mesage;
	private final Date timestamp;
	
	
	public ErrorDto(String mesage, Date timestamp) {
		super();
		this.mesage = mesage;
		this.timestamp = timestamp;
	}
	
	public String getMesage() {
		return mesage;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	
	
}
