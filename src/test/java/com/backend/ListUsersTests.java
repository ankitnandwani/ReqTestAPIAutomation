package com.backend;

import com.backend.Entities.Request.ListUsers;
import com.backend.Entities.Request.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ListUsersTests extends BaseTest{

    public static final Logger log = LogManager.getLogger(ListUsersTests.class);

    @Test
    public void getUsersOnExistingPage(ITestContext context){
        context.setAttribute("ReqTestCaseId", "3271837");
        Map<String, String> params = new HashMap<>();
        params.put("page", "2");
        Response response = requestUtils.sendGetWithQueryParams(getApiPath(Constants.REQRES, Constants.LIST_USERS), getHeaders(), params);
        String responseString = response.getBody().asString();
        log.info("Response : " + responseString);
        ListUsers users = gson.fromJson(responseString, ListUsers.class);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(users.getData().stream().map(User::getFirst_name));

    }
}
