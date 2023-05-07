package com.gibster.pm.service;

import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.model.Doctor;


import com.gibster.repo.nm.model.Nurse;
import com.gibster.repo.pm.dto.DoctorAppointmentDto;
import com.gibster.repo.pm.dto.NurseAppointmentDto;
import com.gibster.repo.pm.dto.PatientDto;
import com.gibster.repo.pm.dto.PatientUpdateDto;
import com.gibster.repo.pm.model.Diagnosis;
import com.gibster.repo.pm.model.Patient;
import java.util.List;

public interface PatientService {
  PatientDto create(Patient patient) throws BusinessLayerException;

  PatientDto update(PatientUpdateDto updateDto) throws BusinessLayerException;

  PatientDto updateDoctorInfo(Long id, Doctor doctor) throws BusinessLayerException;

  PatientDto updateDeletedDoctor(Long id, Doctor doctor) throws BusinessLayerException;

  PatientDto updateNurseInfo(Long id, Nurse nurse) throws BusinessLayerException;

  PatientDto updateDeletedNurse(Long id, Nurse nurse) throws BusinessLayerException;

  PatientDto get(Long id) throws BusinessLayerException;

  List<PatientDto> list() throws BusinessLayerException;

  boolean delete(Long id) throws BusinessLayerException;

  PatientDto appointDoctorForPatient(Long patientId, Long doctorId) throws BusinessLayerException;
  PatientDto appointNurseForPatient(Long patientId, Long nurseId) throws BusinessLayerException;
  PatientDto appointDiagnosisForPatient(Long patientId, Diagnosis diagnosis) throws BusinessLayerException;

  PatientDto assignAnAppointmentByNurse(Long patientId, NurseAppointmentDto nurseAppointmentDto) throws BusinessLayerException;
  PatientDto assignAnAppointmentByDoctor(Long patientId, DoctorAppointmentDto doctorAppointmentDto) throws BusinessLayerException;
  PatientDto dischargePatientByDoctor(Long patientId) throws BusinessLayerException;
}
