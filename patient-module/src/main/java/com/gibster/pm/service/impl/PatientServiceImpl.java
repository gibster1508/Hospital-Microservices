package com.gibster.pm.service.impl;

import com.gibster.pm.feigns.DoctorFeign;
import com.gibster.pm.feigns.HospitalFeign;
import com.gibster.pm.feigns.NurseFeign;
import com.gibster.pm.helper.PopulateHelper;
import com.gibster.pm.repository.PatientRepository;
import com.gibster.pm.service.PatientService;
import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.model.Doctor;
import com.gibster.repo.nm.model.Nurse;
import com.gibster.repo.pm.dto.PatientDto;
import com.gibster.repo.pm.dto.PatientUpdateDto;
import com.gibster.repo.pm.model.Appointment;
import com.gibster.repo.pm.model.Patient;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;
    private final DoctorFeign doctorFeign;
    private final NurseFeign nurseFeign;
    private final HospitalFeign hospitalFeign;

    @Override
    public PatientDto create(Patient patient) throws BusinessLayerException {
        try {
            patient.setDoctors(Collections.emptyList());
            patient.setNurses(Collections.emptyList());
            patient.setDiagnosis("not defined");
            Appointment appointment = new Appointment();
            System.out.println(Arrays.toString(appointment.getClass().getFields()));
            patient.setAppointment(new Appointment("Procedures - not assigned", "medicament - not assigned", "surgery - not assigned"));
            Patient savedPatient = repository.save(patient);
            hospitalFeign.updatePatientInformation(savedPatient.getHospitalId(), savedPatient);
            return PopulateHelper.convertToPatientDto(savedPatient);
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto update(PatientUpdateDto updateDto) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(updateDto.getId()).orElse(null);
            if (Objects.isNull(patient))
                return null;
            hospitalFeign.updateDeletedPatient(patient.getHospitalId(), patient);
            hospitalFeign.updatePatientInformation(updateDto.getHospitalId(), patient);
            return PopulateHelper.convertToPatientDto(repository.save(PopulateHelper.populateFromUpdateDto(patient, updateDto)));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto updateDoctorInfo(Long id, Doctor doctor) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(id).orElse(null);
            if (Objects.isNull(patient))
                return null;
            if (patient.getDoctors().isEmpty()) {
                List<Long> doctorsList = new ArrayList<>();
                doctorsList.add(doctor.getId());
                patient.setDoctors(doctorsList);
            } else {
                List<Long> doctorsList = new ArrayList<>(patient.getDoctors());
                doctorsList.add(doctor.getId());
                patient.setDoctors(doctorsList);
            }
            return PopulateHelper.convertToPatientDto(repository.save(patient));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto updateDeletedDoctor(Long id, Doctor doctor) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(id).orElse(null);
            if (Objects.isNull(patient))
                return null;
            List<Long> doctorsList = new ArrayList<>(patient.getDoctors());
            doctorsList.remove(doctor.getId());
            patient.setDoctors(doctorsList);
            return PopulateHelper.convertToPatientDto(repository.save(patient));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto updateNurseInfo(Long id, Nurse nurse) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(id).orElse(null);
            if (Objects.isNull(patient))
                return null;
            if (patient.getNurses().isEmpty()) {
                List<Long> nursesList = new ArrayList<>();
                nursesList.add(nurse.getId());
                patient.setNurses(nursesList);
            } else {
                List<Long> nursesList = new ArrayList<>(patient.getNurses());
                nursesList.add(nurse.getId());
                patient.setNurses(nursesList);
            }
            return PopulateHelper.convertToPatientDto(repository.save(patient));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto updateDeletedNurse(Long id, Nurse nurse) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(id).orElse(null);
            if (Objects.isNull(patient))
                return null;
            List<Long> nursesList = new ArrayList<>(patient.getNurses());
            nursesList.remove(nurse.getId());
            patient.setNurses(nursesList);
            return PopulateHelper.convertToPatientDto(repository.save(patient));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto get(Long id) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(id).orElse(null);
            if (Objects.isNull(patient))
                return null;
            return PopulateHelper.convertToPatientDto(patient);
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public List<PatientDto> list() throws BusinessLayerException {
        try {
            return repository.findAll().stream().map(PopulateHelper::convertToPatientDto).toList();
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(id).orElse(null);
            if (Objects.isNull(patient))
                return false;
            doctorFeign.updateDeletedHospital(id);
            nurseFeign.updateDeletedHospital(id);
            repository.delete(patient);

            ResponseEntity<String> updateResponse = hospitalFeign.updateDeletedPatient(patient.getHospitalId(), patient);
            return HttpStatus.OK.equals(updateResponse.getStatusCode());
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto appointDoctorForPatient(Long patientId, Long doctorId) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(patientId).orElse(null);
            if (Objects.isNull(patient))
                return null;
            if (patient.getDoctors().isEmpty()) {
                List<Long> doctorsList = new ArrayList<>();
                doctorsList.add(doctorId);
                patient.setDoctors(doctorsList);
            } else {
                List<Long> doctorsList = new ArrayList<>(patient.getDoctors());
                doctorsList.add(doctorId);
                patient.setDoctors(doctorsList);
            }
            return PopulateHelper.convertToPatientDto(repository.save(patient));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }

    @Override
    public PatientDto appointNurseForPatient(Long patientId, Long nurseId) throws BusinessLayerException {
        try {
            Patient patient = repository.findById(patientId).orElse(null);
            if (Objects.isNull(patient))
                return null;
            if (patient.getNurses().isEmpty()) {
                List<Long> nursesList = new ArrayList<>();
                nursesList.add(nurseId);
                patient.setNurses(nursesList);
            } else {
                List<Long> nursesList = new ArrayList<>(patient.getNurses());
                nursesList.add(nurseId);
                patient.setNurses(nursesList);
            }
            return PopulateHelper.convertToPatientDto(repository.save(patient));
        } catch (Exception e) {
            throw new BusinessLayerException(e.getMessage(), e);
        }
    }
}
