package com.gibster.repo.dm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@Setter
public class AddressModel {
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;
    @NotEmpty
    private Long zipCode;
}
