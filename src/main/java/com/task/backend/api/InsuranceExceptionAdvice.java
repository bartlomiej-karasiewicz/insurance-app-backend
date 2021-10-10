package com.task.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InsuranceExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<InsuranceController.ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exception) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new InsuranceController.ResponseMessage("TOO LARGE FILE"));
  }
}
