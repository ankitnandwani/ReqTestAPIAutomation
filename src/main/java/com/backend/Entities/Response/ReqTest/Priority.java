package com.backend.Entities.Response.ReqTest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Priority {
    String value;
    String name;
    int sortIndex;
    String fieldType;
}
