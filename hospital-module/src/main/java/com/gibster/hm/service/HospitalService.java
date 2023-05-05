package com.gibster.hm.service;

import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.model.Doctor;
import com.gibster.repo.hm.dto.HospitalDto;
import com.gibster.repo.hm.dto.HospitalUpdateDto;
import com.gibster.repo.hm.model.Hospital;

import com.gibster.repo.nm.model.Nurse;
import com.gibster.repo.pm.model.Patient;
import java.util.List;

public interface HospitalService {
    HospitalDto create(Hospital hospital) throws BusinessLayerException;

    HospitalDto update(HospitalUpdateDto updateDto) throws BusinessLayerException;

    HospitalDto updateDoctorInfo(Long id, Doctor doctor) throws BusinessLayerException;

    HospitalDto updateDeletedDoctor(Long id, Doctor doctor) throws BusinessLayerException;

    HospitalDto updateNurseInfo(Long id, Nurse nurse) throws BusinessLayerException;

    HospitalDto updateDeletedNurse(Long id, Nurse nurse) throws BusinessLayerException;

    HospitalDto updatePatientInfo(Long id, Patient patient) throws BusinessLayerException;

    HospitalDto updateDeletedPatient(Long id, Patient patient) throws BusinessLayerException;

    HospitalDto get(Long id) throws BusinessLayerException;

    List<HospitalDto> list() throws BusinessLayerException;

    boolean delete(Long hospitalId) throws BusinessLayerException;

}
