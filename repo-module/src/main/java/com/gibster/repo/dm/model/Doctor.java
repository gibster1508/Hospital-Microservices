package com.gibster.repo.dm.model;

import com.gibster.repo.dm.model.enums.DoctorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
public class Doctor {
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
    private DoctorType doctorType;

    @Getter
    @Setter
    @NotEmpty
    @Embedded
    @Column(nullable = false)
    private AddressModel address;

    @Getter
    @Setter
    private Long hospitalId;
}
