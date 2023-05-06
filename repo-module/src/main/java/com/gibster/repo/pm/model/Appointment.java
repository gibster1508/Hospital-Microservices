package com.gibster.repo.pm.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
  @NotEmpty
  private String procedures;
  @NotEmpty
  private String medicament;
  @NotEmpty
  private String surgery;
}

