package com.petHosting.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ResponseUserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
