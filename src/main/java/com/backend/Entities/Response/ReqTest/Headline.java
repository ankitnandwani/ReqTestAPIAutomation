package com.backend.Entities.Response.ReqTest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Headline {
    String value;
    String name;
    String fieldType;
}
