package com.gibster.repo.hm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalUpdateDto {
    @NotNull
    private Long id;
    private String name;
    private String street;
    private String city;
    private Long zipCode;
}
