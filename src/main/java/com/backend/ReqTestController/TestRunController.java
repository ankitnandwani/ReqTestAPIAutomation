package com.backend.ReqTestController;

import com.backend.Constants;
import com.backend.Entities.Response.ReqTest.CreateTestRunResponse;
import com.backend.RequestUtils;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class TestRunController {

    public static final Logger log = LogManager.getLogger(TestRunController.class);
    Gson gson = new Gson();

    RequestUtils requestUtils = new RequestUtils();

    public CreateTestRunResponse createNewTestRun(String url, Map<String, ?> headers, String body){
        log.info("Body : " + body);
        Response response = requestUtils.sendPostWithBodyAndPath(url, headers, getProjectIdParam(), body);
        log.info("Response : " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        return gson.fromJson(response.getBody().asString(), CreateTestRunResponse.class);
    }

    public void addTestcase(String url, Map<String, ?> headers, String body, String testRunId){
        log.info("Body : " + body);
        Response response = requestUtils.sendPostWithBodyAndPath(url, headers, getProjectIdAndTestRunIdParam(testRunId), body);
        log.info("Response : " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    public void getTestRunContents(String url, Map<String, ?> headers, String body, String testRunId){
        log.info("Body : " + body);
        Response response = requestUtils.sendGetWithPathParams(url, headers, getProjectIdAndTestRunIdParam(testRunId));
        log.info("Response : " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    private Map<String, ?> getProjectIdParam(){
        Map<String, String> params = new HashMap<>();
        params.put("projectId", Constants.PROJECT_ID);
        return params;
    }

    private Map<String, ?> getProjectIdAndTestRunIdParam(String testRunId){
        Map<String, String> params = (Map<String, String>) getProjectIdParam();
        params.put("testRunId", testRunId);
        return params;
    }


}
