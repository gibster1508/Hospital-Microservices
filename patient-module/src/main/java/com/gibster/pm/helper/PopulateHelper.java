package com.gibster.pm.helper;



import com.gibster.repo.pm.dto.PatientUpdateDto;
import com.gibster.repo.pm.model.AddressModel;
import com.gibster.repo.pm.dto.PatientDto;
import com.gibster.repo.pm.model.Appointment;
import com.gibster.repo.pm.model.Diagnosis;
import com.gibster.repo.pm.model.Patient;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public final class PopulateHelper {

    private static final String COMMA = ",";

    private PopulateHelper() {
        throw new UnsupportedOperationException();
    }

    public static PatientDto convertToPatientDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .diagnosis(convertToDiagnosisString(patient.getDiagnosis()))
                .address(convertToAddressString(patient.getAddress()))
                .appointment(convertToAppointmentString(patient.getAppointment()))
                .doctors(patient.getDoctors())
                .nurses(patient.getNurses())
                .hospitalId(patient.getHospitalId())
                .isDischarged(patient.getIsDischarged())
                .build();
    }

    public static Patient populateFromUpdateDto(final Patient patient, PatientUpdateDto dto) {
        patient.setName(StringUtils.isEmpty(dto.getName()) ? patient.getName() : dto.getName());
        patient.setHospitalId((dto.getHospitalId() != 0) ?  dto.getHospitalId() : patient.getHospitalId());
        patient.setAddress(populateAddressFromDto(patient.getAddress(), dto));
        return patient;
    }

    private static String convertToAddressString(AddressModel addressModel) {
        return addressModel.getStreet() + COMMA + addressModel.getCity() + COMMA + addressModel.getZipCode();
    }

    private static String convertToDiagnosisString(Diagnosis diagnosis) {
        return diagnosis.getDiagnosis();
    }

    private static String convertToAppointmentString(Appointment appointment) {
        return appointment.getProcedures() + COMMA + appointment.getMedicament() + COMMA + appointment.getSurgery();
    }

    private static AddressModel populateAddressFromDto(final AddressModel addressModel, PatientUpdateDto dto) {
        return AddressModel.builder()
                .city(StringUtils.isEmpty(dto.getCity()) ? addressModel.getCity() : dto.getCity())
                .street(StringUtils.isEmpty(dto.getStreet()) ? addressModel.getStreet() : dto.getStreet())
                .zipCode(Objects.isNull(dto.getZipCode()) ? addressModel.getZipCode() : dto.getZipCode())
                .build();
    }
}
