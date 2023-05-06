package com.gibster.repo.pm.dto;

import javax.validation.constraints.NotEmpty;
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
public class NurseAppointmentDto {
  @NotEmpty
  private String procedures;
  @NotEmpty
  private String medicament;

}
