package com.gibster.hm.service.impl;

import com.gibster.hm.feigns.DoctorFeign;
import com.gibster.hm.feigns.NurseFeign;
import com.gibster.hm.helper.PopulateHelper;
import com.gibster.hm.repository.HospitalRepository;
import com.gibster.hm.service.HospitalService;
import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.model.Doctor;
import com.gibster.repo.hm.dto.HospitalDto;
import com.gibster.repo.hm.dto.HospitalUpdateDto;
import com.gibster.repo.hm.model.Hospital;
import com.gibster.repo.nm.model.Nurse;
import com.gibster.repo.pm.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository repository;
    private final DoctorFeign doctorFeign;
    private final NurseFeign nurseFeign;

    @Override
    public HospitalDto create(Hospital hospital) throws BusinessLayerException {
        try {
            hospital.setDoctors(Collections.emptyList());
            hospital.setNurses(Collections.emptyList());
            hospital.setPatients(Collections.emptyList());
            return PopulateHelper.convertToHospitalDto(repository.save(hospital));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto update(HospitalUpdateDto updateDto) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(updateDto.getId()).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            return PopulateHelper.convertToHospitalDto(repository.save(PopulateHelper.populateFromUpdateDto(hospital, updateDto)));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto updateDoctorInfo(Long id, Doctor doctor) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            if (hospital.getDoctors().isEmpty()) {
                List<Long> doctorsList = new ArrayList<>();
                doctorsList.add(doctor.getId());
                hospital.setDoctors(doctorsList);
            } else {
                List<Long> doctorsList = new ArrayList<>(hospital.getDoctors());
                doctorsList.add(doctor.getId());
                hospital.setDoctors(doctorsList);
            }
            return PopulateHelper.convertToHospitalDto(repository.save(hospital));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto updateDeletedDoctor(Long id, Doctor doctor) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            List<Long> doctorsList = new ArrayList<>(hospital.getDoctors());
            doctorsList.remove(doctor.getId());
            hospital.setDoctors(doctorsList);
            return PopulateHelper.convertToHospitalDto(repository.save(hospital));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto updateNurseInfo(Long id, Nurse nurse) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            if (hospital.getNurses().isEmpty()) {
                List<Long> nursesList = new ArrayList<>();
                nursesList.add(nurse.getId());
                hospital.setNurses(nursesList);
            } else {
                List<Long> nursesList = new ArrayList<>(hospital.getNurses());
                nursesList.add(nurse.getId());
                hospital.setNurses(nursesList);
            }
            return PopulateHelper.convertToHospitalDto(repository.save(hospital));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto updateDeletedNurse(Long id, Nurse nurse) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            List<Long> nursesList = new ArrayList<>(hospital.getNurses());
            nursesList.remove(nurse.getId());
            hospital.setNurses(nursesList);
            return PopulateHelper.convertToHospitalDto(repository.save(hospital));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto updatePatientInfo(Long id, Patient patient) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            if (hospital.getPatients().isEmpty()) {
                List<Long> patientList = new ArrayList<>();
                patientList.add(patient.getId());
                hospital.setPatients(patientList);
            } else {
                List<Long> patientList = new ArrayList<>(hospital.getPatients());
                patientList.add(patient.getId());
                hospital.setPatients(patientList);
            }
            return PopulateHelper.convertToHospitalDto(repository.save(hospital));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto updateDeletedPatient(Long id, Patient patient) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            List<Long> patientList = new ArrayList<>(hospital.getPatients());
            patientList.remove(patient.getId());
            hospital.setPatients(patientList);
            return PopulateHelper.convertToHospitalDto(repository.save(hospital));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public HospitalDto get(Long id) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return null;
            return PopulateHelper.convertToHospitalDto(hospital);
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public List<HospitalDto> list() throws BusinessLayerException {
        try {
            return repository.findAll().stream().map(PopulateHelper::convertToHospitalDto).toList();
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws BusinessLayerException {
        try {
            Hospital hospital = repository.findById(id).orElse(null);
            if (Objects.isNull(hospital))
                return false;
            doctorFeign.updateDeletedHospital(id);
            nurseFeign.updateDeletedHospital(id);
            repository.delete(hospital);
            return true;
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }
}
