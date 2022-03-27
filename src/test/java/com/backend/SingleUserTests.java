package com.backend;

import com.backend.Entities.Request.SingleUser;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class SingleUserTests extends BaseTest{

    public static final Logger log = LogManager.getLogger(SingleUserTests.class);

    @Test
    public void getSingleUserWhoExists(ITestContext context){
        context.setAttribute("ReqTestCaseId", "3271836");
        Response response = requestUtils.sendGet(getApiPath(Constants.REQRES, Constants.LIST_USERS) + "/9", getHeaders());
        String responseString = response.getBody().asString();
        log.info("Response : " + responseString);
        SingleUser user = gson.fromJson(responseString, SingleUser.class);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(user.getData().getFirst_name(), "Tobias");
    }
}
