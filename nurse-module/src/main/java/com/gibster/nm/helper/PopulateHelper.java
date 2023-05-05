package com.gibster.nm.helper;


import com.gibster.repo.dm.model.AddressModel;
import com.gibster.repo.nm.dto.NurseDto;
import com.gibster.repo.nm.model.Nurse;

public final class PopulateHelper {

    private static final String COMMA = ",";

    private PopulateHelper() {
        throw new UnsupportedOperationException();
    }

    public static NurseDto convertToNurseDto(Nurse nurse) {
        return NurseDto.builder()
                .id(nurse.getId())
                .name(nurse.getName())
                .address(convertToAddressString(nurse.getAddress()))
                .hospitalId(nurse.getHospitalId()).build();
    }

    private static String convertToAddressString(AddressModel addressModel) {
        return addressModel.getStreet() + COMMA + addressModel.getCity() + COMMA + addressModel.getZipCode();
    }
}
