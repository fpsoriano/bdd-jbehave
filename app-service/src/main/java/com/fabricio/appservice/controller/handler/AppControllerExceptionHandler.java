package com.fabricio.appservice.controller.handler;

import com.fabricio.appservice.model.ErrorModel;
import com.fabricio.appservice.service.excpetion.AppErrorCode;
import com.fabricio.appservice.service.excpetion.AppNotFoundException;
import com.fabricio.appservice.service.excpetion.EntityAlreadyExistsException;
import com.fabricio.appservice.service.excpetion.ErrorMessages;
import com.fabricio.appservice.service.excpetion.InvalidAppCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AppControllerExceptionHandler {

  @ExceptionHandler(InvalidAppCreateException.class)
  protected ResponseEntity<ErrorModel> handleBadRequest(InvalidAppCreateException ex) {
    return new ResponseEntity<>(ex.getError(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityAlreadyExistsException.class)
  protected ResponseEntity<ErrorModel> handleBadRequest(EntityAlreadyExistsException ex) {
    return new ResponseEntity<>(ex.getError(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AppNotFoundException.class)
  protected ResponseEntity<ErrorModel> handleBadRequest(AppNotFoundException ex) {
    return new ResponseEntity<>(ex.getError(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorModel> handleBadRequest(Exception ex) {
    return new ResponseEntity<>(new ErrorModel(AppErrorCode.INTERNAL_SERVER_ERROR,
        ErrorMessages.INTERNAL_ERROR ), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
