package com.fabricio.appservice.service;

import com.fabricio.appservice.model.AppModel;
import com.fabricio.appservice.service.excpetion.AppErrorCode;
import com.fabricio.appservice.service.excpetion.AppNotFoundException;
import com.fabricio.appservice.service.excpetion.EntityAlreadyExistsException;
import com.fabricio.appservice.service.excpetion.ErrorMessages;
import com.fabricio.appservice.service.validation.AppValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AppService {

  private static Map<String, AppModel> appsReference = new ConcurrentHashMap<>();

  public AppModel createApp(AppModel appModel) {
    AppValidation.appCreateValidation(appModel);

    if (appsReference.containsKey(appModel.getAppId())) {
      throw new EntityAlreadyExistsException(AppErrorCode.APP_ALREADY_EXISTS, String.format(ErrorMessages.APP_ALREADY_EXISTS, appModel.getAppId()));
    }
    appsReference.put(appModel.getAppId(), appModel);
    return appModel;
  }

  public AppModel getApp(String appId) {
    AppModel appModelReference = appsReference.get(appId);
    if (appModelReference == null) {
      throw new AppNotFoundException(AppErrorCode.APP_NOT_FOUND, String.format(ErrorMessages.APP_NOT_FOUND, appId));
    }
    return appModelReference;
  }

  public List<AppModel> getApps() {
    List<AppModel> appModels = new ArrayList(appsReference.values());
    return appModels;
  }

  public void deleteApp(String appId) {
    AppModel appModelReference = appsReference.remove(appId);
    if (appModelReference == null) {
      throw new AppNotFoundException(AppErrorCode.APP_NOT_FOUND, String.format(ErrorMessages.APP_NOT_FOUND, appId));
    }
  }


}
