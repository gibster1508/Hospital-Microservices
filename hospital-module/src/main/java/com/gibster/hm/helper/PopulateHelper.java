package com.gibster.hm.helper;


import com.gibster.repo.hm.dto.HospitalDto;
import com.gibster.repo.hm.dto.HospitalUpdateDto;
import com.gibster.repo.hm.model.AddressModel;
import com.gibster.repo.hm.model.Hospital;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public final class PopulateHelper {

    private static final String COMMA = ",";

    private PopulateHelper() {
        throw new UnsupportedOperationException();
    }

    public static HospitalDto convertToHospitalDto(Hospital hospital) {
        return HospitalDto.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .hospitalType(String.valueOf(hospital.getHospitalType()))
                .address(convertToAddressString(hospital.getAddress()))
                .doctors(hospital.getDoctors())
                .nurses(hospital.getNurses())
                .patients(hospital.getPatients())
                .build();
    }

    public static Hospital populateFromUpdateDto(final Hospital hospital, HospitalUpdateDto dto) {
        hospital.setName(StringUtils.isEmpty(dto.getName()) ? hospital.getName() : dto.getName());
        hospital.setAddress(populateAddressFromDto(hospital.getAddress(), dto));
        return hospital;
    }

    private static String convertToAddressString(AddressModel addressModel) {
        return addressModel.getStreet() + COMMA + addressModel.getCity() + COMMA + addressModel.getZipCode();
    }

    private static AddressModel populateAddressFromDto(final AddressModel addressModel, HospitalUpdateDto dto) {
        return AddressModel.builder()
                .city(StringUtils.isEmpty(dto.getCity()) ? addressModel.getCity() : dto.getCity())
                .street(StringUtils.isEmpty(dto.getStreet()) ? addressModel.getStreet() : dto.getStreet())
                .zipCode(Objects.isNull(dto.getZipCode()) ? addressModel.getZipCode() : dto.getZipCode())
                .build();
    }
}
