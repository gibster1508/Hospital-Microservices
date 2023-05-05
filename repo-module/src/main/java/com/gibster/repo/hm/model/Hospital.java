package com.gibster.repo.hm.model;

import com.gibster.repo.hm.model.enums.HospitalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@NoArgsConstructor
public class Hospital {
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
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private HospitalType hospitalType;

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
    @ElementCollection
    private List<Long> patients;
}
