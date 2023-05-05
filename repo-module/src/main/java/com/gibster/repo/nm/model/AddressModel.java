package com.gibster.repo.nm.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

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
