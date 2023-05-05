package com.gibster.repo.pm.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
public class Patient {
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
  @ElementCollection
  private List<Long> doctors;

  @Getter
  @Setter
  @ElementCollection
  private List<Long> nurses;

  @Getter
  @Setter
  private Long hospitalId;
}
