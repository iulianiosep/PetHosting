package com.petHosting.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String reTypedPassword;
    private Boolean registerAsGuest;
}
