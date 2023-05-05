package com.gibster.repo.pm.dto;

import java.util.List;
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
public class PatientDto {
  private Long id;
  private String name;
  private String address;
  private List<Long> doctors;
  private List<Long> nurses;
  private Long hospitalId;
}
