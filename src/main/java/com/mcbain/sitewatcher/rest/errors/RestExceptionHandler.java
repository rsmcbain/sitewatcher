package com.mcbain.sitewatcher.rest.errors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mcbain.sitewatcher.rest.exceptions.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired	
	private MessageSource messageSource;
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(NotFoundException nfe, HttpServletRequest request) {
		ErrorDetail errorDetail = new ErrorDetail("Resource Not Found"
				                                 ,HttpStatus.NOT_FOUND.value()
				                                 ,nfe.getMessage()
				                                 ,new Date().getTime()
				                                 ,nfe.getClass().getSimpleName());
		
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail("Validation Failed"
                ,HttpStatus.BAD_REQUEST.value()
                ,"Validation Failed"
                ,new Date().getTime()
                ,manve.getClass().getSimpleName());

        List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrors) {
        	List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
        	if (validationErrorList == null) {
        		validationErrorList = new ArrayList<>();
        		errorDetail.getErrors().put(fe.getField(), validationErrorList);
        	}
        	ValidationError validationError = new ValidationError(fe.getCode(),messageSource.getMessage(fe, null));
        	validationErrorList.add(validationError);
        }
        
        return handleExceptionInternal(manve, errorDetail, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail("Message Not Readable"
                ,status.value()
                ,ex.getMessage()
                ,new Date().getTime()
                ,ex.getClass().getName());
		
		return handleExceptionInternal(ex, errorDetail, headers, status, request);
	}
}
