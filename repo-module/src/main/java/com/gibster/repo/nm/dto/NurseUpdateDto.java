package com.gibster.repo.nm.dto;

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
public class NurseUpdateDto {
  @NotNull
  private Long id;
  @NotNull
  private Long hospitalId;
}
