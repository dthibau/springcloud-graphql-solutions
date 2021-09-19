package org.formation.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotFoundAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { MemberNotFoundException.class })
	ResponseEntity<Object> handleNotFoundException(HttpServletRequest request, Throwable ex) {
		ErrorDto error = new ErrorDto(ex.getMessage(), new Date());
		return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}


}
