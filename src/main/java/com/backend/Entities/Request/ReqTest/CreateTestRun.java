package com.backend.Entities.Request.ReqTest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTestRun {
    Fields fields;
}