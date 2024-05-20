package com.devsuperior.dscommerce.controllers.handlers;

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.dto.ValidationError;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<CustomError> resourceNotFound(
      ResourceNotFoundException e,
      HttpServletRequest request
  ){
    HttpStatus status = HttpStatus.NOT_FOUND;
    CustomError error = new CustomError(
        Instant.now(),
        status.value(),
        e.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<CustomError> database(
      DatabaseException e,
      HttpServletRequest request
  ) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    CustomError error = new CustomError(
        Instant.now(),
        status.value(),
        e.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<CustomError> forbidden(ForbiddenException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationError error = new ValidationError(
        Instant.now(),
        status.value(),
        "invalid data",
        request.getRequestURI()
    );
    for(FieldError f : e.getBindingResult().getFieldErrors()) {
      error.addError(f.getField(), f.getDefaultMessage());
    }
    return ResponseEntity.status(status).body(error);
  }
}
