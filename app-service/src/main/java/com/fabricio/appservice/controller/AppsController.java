package com.fabricio.appservice.controller;

import com.fabricio.appservice.controller.handler.AppControllerExceptionHandler;
import com.fabricio.appservice.model.AppModel;
import com.fabricio.appservice.model.ErrorModel;
import com.fabricio.appservice.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.websocket.server.PathParam;
import javax.xml.ws.Response;

@RestController
@RequestMapping(value = "/apps", produces = "application/json")
@Api(value = "Apps", description = "Applications")
public class AppsController extends AppControllerExceptionHandler {

  private final AppService appService;

  @Autowired
  AppsController(AppService appService) {
    this.appService = appService;
  }


  @ApiOperation(value = "Create application",response = AppModel.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "App created successfully.", response = ErrorModel.class),
      @ApiResponse(code = 400, message = "Failed to created app.", response = ErrorModel.class),
      @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorModel.class)
  }
  )
  @PostMapping
  public ResponseEntity<AppModel> createApp(@RequestBody AppModel appModel) {
    AppModel response = appService.createApp(appModel);
    return new ResponseEntity(response, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Get application by appId",response = AppModel.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "App created successfully.", response = ErrorModel.class),
      @ApiResponse(code = 400, message = "Failed to get app.", response = ErrorModel.class),
      @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorModel.class)
  }
  )
  @GetMapping("/{appId}")
  public ResponseEntity<AppModel> getApp(@PathVariable String appId) {
    AppModel response = appService.getApp(appId);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @ApiOperation(value = "Get all applications",response = AppModel.class, responseContainer="List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "App created successfully.", response = ErrorModel.class),
      @ApiResponse(code = 400, message = "Failed to get app.", response = ErrorModel.class),
      @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorModel.class)
  }
  )
  @GetMapping("/available")
  public ResponseEntity<AppModel> getApps() {
    List<AppModel> response = appService.getApps();
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @ApiOperation(value = "Delete application by appId",response = AppModel.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "App created successfully.", response = ErrorModel.class),
      @ApiResponse(code = 400, message = "Failed to get app.", response = ErrorModel.class),
      @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorModel.class)
  }
  )
  @DeleteMapping("/{appId}")
  public ResponseEntity deleteApp(@PathVariable String appId) {
    appService.deleteApp(appId);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
