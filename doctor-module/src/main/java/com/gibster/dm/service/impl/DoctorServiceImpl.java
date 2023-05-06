package com.gibster.dm.service.impl;

import com.gibster.dm.feigns.HospitalFeign;
import com.gibster.dm.helper.PopulateHelper;
import com.gibster.dm.repository.DoctorRepository;
import com.gibster.dm.service.DoctorService;
import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.dto.DoctorDto;
import com.gibster.repo.dm.model.Doctor;
import com.gibster.repo.pm.model.Patient;
import java.util.ArrayList;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository repository;
    private final HospitalFeign hospitalFeign;

    @Override
    public DoctorDto create(Doctor doctor) throws BusinessLayerException {
        try {
            doctor.setPatients(Collections.emptyList());
            Doctor savedEntity = repository.save(doctor);
            hospitalFeign.updateDoctorInformation(savedEntity.getHospitalId(), savedEntity);
            return PopulateHelper.convertToDoctorDto(savedEntity);
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public DoctorDto get(Long id) throws BusinessLayerException {
        try {
            Doctor doctor = repository.findById(id).orElse(null);
            if (Objects.isNull(doctor))
                return null;
            return PopulateHelper.convertToDoctorDto(doctor);
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public DoctorDto update(Long id, Long hospitalId) throws BusinessLayerException {
        try {
            Doctor doctor = repository.findById(id).orElse(null);
            if (Objects.isNull(doctor))
                return null;
            hospitalFeign.updateDeletedDoctor(doctor.getHospitalId(), doctor);
            doctor.setHospitalId(hospitalId);
            hospitalFeign.updateDoctorInformation(hospitalId, doctor);
            return PopulateHelper.convertToDoctorDto(repository.save(doctor));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateDeletedHospital(Long hospitalId) throws BusinessLayerException {
        try {
            repository.findDoctorsByHospitalId(hospitalId).forEach(doctor -> {
                doctor.setHospitalId(null);
                repository.save(doctor);
            });
            return true;
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long doctorId) throws BusinessLayerException {
        try {
            Doctor doctor = repository.findById(doctorId).orElse(null);
            if (Objects.isNull(doctor))
                return false;
            repository.delete(doctor);
            ResponseEntity<String> updateResponse = hospitalFeign.updateDeletedDoctor(doctor.getHospitalId(), doctor);
            return HttpStatus.OK.equals(updateResponse.getStatusCode());
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public List<DoctorDto> list() throws BusinessLayerException {
        try {
            return repository.findAll().stream().map(PopulateHelper::convertToDoctorDto).toList();
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public DoctorDto updatePatientInfo(Long id, Patient patient) throws BusinessLayerException {
        try {
            Doctor doctor = repository.findById(id).orElse(null);
            if (Objects.isNull(doctor))
                return null;
            if (doctor.getPatients().isEmpty()) {
                List<Long> patientList = new ArrayList<>();
                patientList.add(patient.getId());
                doctor.setPatients(patientList);
            } else {
                List<Long> patientList = new ArrayList<>(doctor.getPatients());
                patientList.add(patient.getId());
                doctor.setPatients(patientList);
            }
            return PopulateHelper.convertToDoctorDto(repository.save(doctor));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public DoctorDto updateDeletedPatient(Long id, Patient patient) throws BusinessLayerException {
        try {
            Doctor doctor = repository.findById(id).orElse(null);
            if (Objects.isNull(doctor))
                return null;
            List<Long> patientList = new ArrayList<>(doctor.getPatients());
            patientList.remove(patient.getId());
            doctor.setPatients(patientList);
            return PopulateHelper.convertToDoctorDto(repository.save(doctor));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public DoctorDto appointPatientForDoctor(Long doctorId, Long patientId) throws BusinessLayerException {
        try {
            Doctor doctor = repository.findById(doctorId).orElse(null);
            if (Objects.isNull(doctor))
                return null;
            if (doctor.getPatients().isEmpty()) {
                List<Long> patientList = new ArrayList<>();
                patientList.add(patientId);
                doctor.setPatients(patientList);
            } else {
                List<Long> patientList = new ArrayList<>(doctor.getPatients());
                patientList.add(patientId);
                doctor.setPatients(patientList);
            }
            return PopulateHelper.convertToDoctorDto(repository.save(doctor));
        }catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

}
