package com.backend.Entities.Response.ReqTest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTestRunResponse {
    boolean isSuccess;
    String message;
    Result result;
}
