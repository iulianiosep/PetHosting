package com.petHosting.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModifyUserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> roles;
}
