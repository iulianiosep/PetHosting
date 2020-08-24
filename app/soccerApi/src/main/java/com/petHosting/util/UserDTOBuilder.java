package com.petHosting.util;

import com.petHosting.dto.ResponseUserDTO;
import com.petHosting.entity.User;

import java.util.stream.Collectors;

public class UserDTOBuilder {

    public static ResponseUserDTO build(User user){
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(user.getId());
        responseUserDTO.setLastName(user.getLastName());
        responseUserDTO.setFirstName(user.getLastName());
        responseUserDTO.setEmail(user.getEmail());
        responseUserDTO.setRoles(user.getRoles().stream()
                .map(role->role.getRole()).collect(Collectors.toSet()));
        return responseUserDTO;
    }
}
