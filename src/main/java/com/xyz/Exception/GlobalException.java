package com.xyz.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(SellerException.class)
	public ResponseEntity<ErrorDetails> sellerExcetionHandler(SellerException se, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError(se.getMessage());
		errorDetails.setDetails(request.getDescription(false));;
		errorDetails.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.OK);
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorDetails> ProductException(SellerException se, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError(se.getMessage());
		errorDetails.setDetails(request.getDescription(false));;
		errorDetails.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.OK);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> allExcetionHandler(Exception exp, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError(exp.getMessage());
		errorDetails.setDetails(request.getDescription(false));;
		errorDetails.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.OK);
	}

}
