package org.example.webrise.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
}
