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
public class AddressModel {
  @NotEmpty
  private String street;
  @NotEmpty
  private String city;
  @NotEmpty
  private Long zipCode;
}
