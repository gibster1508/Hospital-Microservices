package com.gibster.hm.feigns;

import javax.transaction.Transactional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "patient-module")
public interface PatientFeign {
  @PutMapping(path = "/api/patient/{patientId}/appoint/doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  ResponseEntity<String> appointDoctorForPatient(@PathVariable Long patientId, @PathVariable Long doctorId);

  @PutMapping(path = "/api/patient/{patientId}/appoint/nurse/{nurseId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  ResponseEntity<String> appointNurseForPatient(@PathVariable Long patientId, @PathVariable Long nurseId);
}
