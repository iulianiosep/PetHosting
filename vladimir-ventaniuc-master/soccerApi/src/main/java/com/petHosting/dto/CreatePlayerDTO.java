package com.petHosting.dto;

import com.petHosting.enums.PlayerPosition;
import lombok.Data;

@Data
public class CreatePlayerDTO {
    private Long teamId;
    private String firstName;
    private String lastName;
    private String country;
    private Integer age;
    private Double value;
    private PlayerPosition position;
}
