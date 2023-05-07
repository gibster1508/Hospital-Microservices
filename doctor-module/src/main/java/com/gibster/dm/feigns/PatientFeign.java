package com.gibster.dm.feigns;

import com.gibster.repo.pm.dto.DoctorAppointmentDto;
import com.gibster.repo.pm.dto.PatientDto;
import com.gibster.repo.pm.model.Diagnosis;
import javax.transaction.Transactional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "patient-module")
public interface PatientFeign {
  @PostMapping(path = "/api/patient/{patientId}/appoint/diagnosis", produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  ResponseEntity<PatientDto> appointDiagnosisForPatient(@PathVariable Long patientId, @RequestBody Diagnosis diagnosis);

  @PostMapping(path = "/api/patient/{patientId}/doctor/assign/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PatientDto> assignAnAppointmentByDoctor(@PathVariable Long patientId,
                                                         @RequestBody DoctorAppointmentDto doctorAppointmentDto);

  @PostMapping(path = "/api/patient/{patientId}/discharge", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PatientDto> dischargePatientByDoctor(@PathVariable Long patientId);
}
