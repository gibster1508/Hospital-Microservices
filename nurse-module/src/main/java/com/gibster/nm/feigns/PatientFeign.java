package com.gibster.nm.feigns;

import com.gibster.repo.pm.dto.NurseAppointmentDto;
import com.gibster.repo.pm.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "patient-module")
public interface PatientFeign {
  @PostMapping(path = "/api/patient/{patientId}/nurse/assign/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PatientDto> assignAnAppointmentByNurse(@PathVariable Long patientId,
                                                    @RequestBody NurseAppointmentDto nurseAppointmentDto);
}
