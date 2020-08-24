package com.petHosting.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDTO {

    private Long id;
    private String name;
    private String country;
    private Double value;
    private Double balance;
    private List<PlayerDTO> players;
    private ResponseUserDTO user;
}
