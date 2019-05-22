package com.fabricio.appservice.service.excpetion;

import com.fabricio.appservice.model.ErrorModel;

public class EntityAlreadyExistsException extends RuntimeException{

  private final ErrorModel errorModel;

  public EntityAlreadyExistsException(AppErrorCode appErrorCode, String message) {
    super();
    this.errorModel = new ErrorModel(appErrorCode, message);
  }

  public ErrorModel getError() {
    return errorModel;
  }
}
