package com.backend.Entities.Response.ReqTest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetContentsResponse {
    boolean isSuccess;
    String message;

}
