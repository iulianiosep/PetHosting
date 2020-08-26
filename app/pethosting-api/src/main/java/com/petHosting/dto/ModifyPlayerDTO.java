package com.petHosting.dto;

import lombok.Data;

@Data
public class ModifyPlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
    private Integer age;
    private String playerPosition;
    private Double value;
}
