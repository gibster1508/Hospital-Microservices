package com.gibster.repo.hm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {
    private Long id;
    private String name;
    private String hospitalType;
    private String address;
    private List<Long> doctors;
    private List<Long> nurses;
    private List<Long> patients;
}
