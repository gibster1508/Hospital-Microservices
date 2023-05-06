package com.gibster.repo.nm.model;

import com.gibster.repo.dm.model.AddressModel;
import java.util.List;
import javax.persistence.ElementCollection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
public class Nurse {
  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @NotEmpty
  @Column(nullable = false)
  private String name;

  @Getter
  @Setter
  @NotEmpty
  @Embedded
  @Column(nullable = false)
  private AddressModel address;

  @Getter
  @Setter
  private Long hospitalId;

  @Getter
  @Setter
  @ElementCollection
  private List<Long> patients;
}
