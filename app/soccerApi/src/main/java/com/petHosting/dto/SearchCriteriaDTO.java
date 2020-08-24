package com.petHosting.dto;

import lombok.Data;

@Data
public class SearchCriteriaDTO {
    private String firstName;
    private String lastName;
    private String Country;
    private Long minValue;
    private Long maxValue;
    private String teamName;
}
