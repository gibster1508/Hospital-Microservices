package com.gibster.dm.feigns;

import com.gibster.repo.dm.model.Doctor;
import com.gibster.repo.hm.dto.HospitalUpdateDto;
import com.gibster.repo.hm.model.Hospital;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

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

    @PutMapping(path = "/api/hospital/updated-doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateDoctorInformation(@PathVariable Long id, @RequestBody Doctor doctor);

    @PutMapping(path = "/api/hospital/deleted-doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateDeletedDoctor(@PathVariable Long id, @RequestBody Doctor doctor);
}
