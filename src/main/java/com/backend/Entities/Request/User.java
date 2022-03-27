package com.backend.Entities.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    int id;
    String email;
    String first_name;
    String last_name;
    String avatar;
}
