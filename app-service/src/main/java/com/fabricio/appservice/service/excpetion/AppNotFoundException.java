package com.fabricio.appservice.service.excpetion;

import com.fabricio.appservice.model.ErrorModel;

public class AppNotFoundException extends RuntimeException{

  private final ErrorModel errorModel;

  public AppNotFoundException(AppErrorCode appErrorCode, String message) {
    super();
    this.errorModel = new ErrorModel(appErrorCode, message);
  }

  public ErrorModel getError() {
    return errorModel;
  }
}
