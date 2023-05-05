package com.gibster.pm.feigns;

import com.gibster.repo.hm.dto.HospitalUpdateDto;
import com.gibster.repo.hm.model.Hospital;
import com.gibster.repo.pm.model.Patient;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hospital-module")
public interface HospitalFeign {
    @PostMapping(path = "/api/hospital", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(@Valid @RequestBody Hospital hospital);

    @GetMapping(path = "/api/hospital/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> get(@PathVariable Long id);

    @GetMapping(path = "/api/hospital", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> list();

    @PutMapping(path = "/api/hospital", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> update(@Valid @RequestBody HospitalUpdateDto dto);

    @PutMapping(path = "/api/hospital/updated-patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updatePatientInformation(@PathVariable Long id, @RequestBody Patient patient);

    @PutMapping(path = "/api/hospital/deleted-patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateDeletedPatient(@PathVariable Long id, @RequestBody Patient patient);
}
