package com.gibster.repo.pm.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateDto {
  @NotNull
  private Long id;
  private String name;
  private String street;
  private String city;
  private Long zipCode;

  @NotNull
  private Long hospitalId;
}
