package com.backend;

public interface Constants {
    String LIST_USERS = "/api/users";
    String CONFIG_PATH = "src/main/resources/config/";
    String REQRES = "REQRES";
    String REQTEST = "REQTEST";
    String CREATE_TEST_RUN = "/{projectId}/testruns";
    String REQTEST_PAT = "f5kOOHW8cKE4NZh5pOQKmzwkenmXPMc6b0mNZC5YZO4g8NL2iyyADxqB7amr4MnZ";
    String PROJECT_ID = "69985";
    String ADD_TEST_CASE = "/{projectId}/testruns/{testRunId}/contents/add-testcases";
    String GET_CONTENTS = "/{projectId}/testruns/{testRunId}/contents";
    String EXECUTE_CONTENT = "/{projectId}/testruns/{testRunId}/contents/execute";
}
