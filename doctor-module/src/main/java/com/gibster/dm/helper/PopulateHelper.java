package com.gibster.dm.helper;

import com.gibster.repo.dm.dto.DoctorDto;
import com.gibster.repo.dm.model.AddressModel;
import com.gibster.repo.dm.model.Doctor;

public final class PopulateHelper {

    private static final String COMMA = ",";

    private PopulateHelper() {
        throw new UnsupportedOperationException();
    }

    public static DoctorDto convertToDoctorDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .type(String.valueOf(doctor.getDoctorType()))
                .address(convertToAddressString(doctor.getAddress()))
                .hospitalId(doctor.getHospitalId()).build();
    }

    private static String convertToAddressString(AddressModel addressModel) {
        return addressModel.getStreet() + COMMA + addressModel.getCity() + COMMA + addressModel.getZipCode();
    }
}
