package com.petHosting.dto;

import com.petHosting.enums.PlayerPosition;
import lombok.Data;

@Data
public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
    private Integer age;
    private Double value;
    private PlayerPosition position;
    private Long teamId;
    private boolean onTransferList;
    private Double price;
    private String teamName;
}
