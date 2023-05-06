package com.gibster.repo.nm.dto;

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
public class NurseDto {
  private Long id;
  private String name;
  private String address;
  private Long hospitalId;
  private List<Long> patients;
}