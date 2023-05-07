package com.gibster.dm.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "nurse-module")
public interface NurseFeign {
  @PutMapping(path = "/api/nurse/{nurseId}/discharged-patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> updateDischargedPatient(@PathVariable Long nurseId, @PathVariable Long patientId);
}
