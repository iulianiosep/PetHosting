package com.petHosting.dto;

import lombok.Data;

@Data
public class ModifyTeamDTO {
    private Long id;
    private String name;
    private String country;
    private Double balance;
}
