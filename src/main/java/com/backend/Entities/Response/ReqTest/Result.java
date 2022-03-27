package com.backend.Entities.Response.ReqTest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    String type;
    long id;
    int customId;
    String createdBy;
    long createdByUserId;
    String createdDate;
    String changedDate;
    boolean archived;
}
