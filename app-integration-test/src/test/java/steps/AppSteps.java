package steps;

import client.AppsAPI;
import exception.ApiException;
import model.AppErrorCode;
import model.AppModel;
import org.apache.commons.lang.RandomStringUtils;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.Response;


public class AppSteps extends Steps {

  private static Map<String, AppModel> appsReference = new ConcurrentHashMap<>();


  @Then("A new application $appId can be created")
  public void createApp(String appId){

    AppModel appModelRequest = generateAppModel(appId);
    AppModel appModelResponse = new AppsAPI().createApp(appModelRequest);
    assertAppFields(appModelRequest, appModelResponse);
    appsReference.put(appId, appModelRequest);
  }


  @Then("A new application cannot be created without $appField")
  public void createAppWithoutAppField(String appField){
    AppModel appModel = generateAppModel("appTest");

    if (appField.equals("appId")) appModel.setAppId(null);

    try {
      new AppsAPI().createApp(appModel);
      Assert.fail("Expected BAD_REQUEST");
    } catch (ApiException e) {
      Assert.assertEquals("Unexpected Response status: ", Response.Status.BAD_REQUEST.getStatusCode(), e.getErrorCode());
      Assert.assertEquals("Unexpected Code Error: ", AppErrorCode.VALIDATION_ERROR_CREATE_APP.name(), e.getError().getCode());
      Assert.assertEquals("Unexpected Message Error: ", appField.concat(" is a required attribute"), e.getError().getMessage());
    }
  }

  private AppModel generateAppModel(String appId) {
    AppModel appModel = new AppModel();
    appModel.setAppId(appId + RandomStringUtils.randomAlphabetic(5));
    appModel.setDescription("test");
    appModel.setName("test");
    appModel.setEnable(true);
    return appModel;
  }

  private void assertAppFields(AppModel appModelRequest, AppModel appModelResponse) {
    Assert.assertNotNull("Response cannot be null", appModelResponse);
    Assert.assertEquals("Match appid", appModelRequest.getAppId(), appModelResponse.getAppId());
    Assert.assertEquals("Match description", appModelRequest.getDescription(), appModelResponse.getDescription());
    Assert.assertEquals("Match name", appModelRequest.getName(), appModelResponse.getName());
    Assert.assertEquals("Match enable", appModelRequest.getEnable(), appModelResponse.getEnable());
  }

}
