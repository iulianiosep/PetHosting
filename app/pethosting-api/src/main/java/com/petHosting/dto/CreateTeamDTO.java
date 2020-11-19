package com.petHosting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTeamDTO {
    private String country;
    private String name;
}
