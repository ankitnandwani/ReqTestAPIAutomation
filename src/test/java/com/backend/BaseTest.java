package com.backend;

import com.backend.Entities.Request.ReqTest.CreateTestRun;
import com.backend.Entities.Request.ReqTest.Fields;
import com.backend.Entities.Request.ReqTest.Name;
import com.backend.Entities.Response.ReqTest.Content;
import com.backend.Entities.Response.ReqTest.CreateTestRunResponse;
import com.backend.Entities.Response.ReqTest.GetContentsResponse;
import com.backend.ReqTestController.TestRunController;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

public class BaseTest {

    public static final Logger log = LogManager.getLogger(BaseTest.class);
    RequestUtils requestUtils = new RequestUtils();
    Gson gson = new Gson();
    Properties properties = new Properties();
    String base_url;
    TestRunController testRunController = new TestRunController();

    public Map<String, ?> getHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    @Parameters({"env"})
    @BeforeSuite
    public void suiteSetup(@Optional("test") String env, ITestContext context){
        log.info("###############    Setting up environment : {}", env + "       #############");
        String configFile = Constants.CONFIG_PATH + env.toLowerCase() + ".properties";
        initializeEnvProperties(configFile);

        String executionTitle = "API Execution - " + RandomUtils.nextInt();

        CreateTestRunResponse response = testRunController.createNewTestRun(
                getApiPath(Constants.REQTEST, Constants.CREATE_TEST_RUN),
                getReqtestHeaders(),
                getCreateTestRunBody(executionTitle));
        context.setAttribute("testRunId", response.getResult().getId());
    }

    @Parameters({"env"})
    @BeforeMethod
    public void beforeMethodSetup(@Optional("test") String env){
        log.info("###############    Setting up environment : {}", env + "       #############");
        String configFile = Constants.CONFIG_PATH + env.toLowerCase() + ".properties";
        initializeEnvProperties(configFile);
    }

    @AfterMethod
    public void setUp(ITestContext context, ITestResult result){

        String testRunId = String.valueOf(context.getAttribute("testRunId"));
        String testResult = result.getStatus() == ITestResult.SUCCESS ? "OK" : "Failed";

        testRunController.addTestcase(
                getApiPath(Constants.REQTEST, Constants.ADD_TEST_CASE),
                getReqtestHeaders(),
                getArrayBody((String) context.getAttribute("ReqTestCaseId")),
                testRunId);

        GetContentsResponse contentsResponse = testRunController.getTestRunContents(
                getApiPath(Constants.REQTEST, Constants.GET_CONTENTS),
                getReqtestHeaders(),
                testRunId);

        Predicate<Content> findWithTestCaseName = e -> e.getName().equals(result.getName());
        long contentId = contentsResponse.getResult().getContents().stream()
                .filter(findWithTestCaseName).findFirst().get().getId();

        testRunController.executeContent(
                getApiPath(Constants.REQTEST, Constants.EXECUTE_CONTENT),
                getReqtestHeaders(),
                getResultQueryParams(testResult),
                testRunId,
                getArrayBody(String.valueOf(contentId)));
    }

    public void initializeEnvProperties(String configFile){
        try {
            properties.load(new FileReader(configFile));
            log.info("###########   Properties file : " + properties);
        } catch (IOException e) {
            log.error("Error while loading properties file");
        }
    }

    public String getApiPath(String url, String path){
        base_url = properties.getProperty(url + "_BASE_URL");
        return base_url + path;
    }

    public Map<String, ?> getReqtestHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("reqtest-pat", Constants.REQTEST_PAT);
        return headers;
    }

    private String getCreateTestRunBody(String testRunTitle){
        Name name = Name.builder().value(testRunTitle).build();
        Fields fields = Fields.builder().Name(name).build();
        CreateTestRun testRun = CreateTestRun.builder().fields(fields).build();
        return gson.toJson(testRun);
    }

    private String getArrayBody(String id){
        return "[ " + id + " ]";
    }

    private Map<String, String> getResultQueryParams(String result){
        Map<String, String> qParams = new HashMap<>();
        qParams.put("result", result);
        return qParams;
    }
}
