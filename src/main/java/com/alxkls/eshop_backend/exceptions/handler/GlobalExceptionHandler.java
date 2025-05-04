package com.alxkls.eshop_backend.exceptions.handler;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.alxkls.eshop_backend.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e) {
    return ResponseEntity.status(FORBIDDEN).body(new ApiResponse(e.getMessage(), null));
  }
}
