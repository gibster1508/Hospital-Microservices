package com.gibster.hm.feigns;

import com.gibster.repo.dm.dto.DoctorUpdateDto;
import com.gibster.repo.dm.model.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "doctor-module")
public interface DoctorFeign {
    @PostMapping(path = "/api/doctor", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(@Valid @RequestBody Doctor doctor);

    @GetMapping(path = "/api/doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> get(@PathVariable Long id);

    @DeleteMapping(path = "/api/doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> delete(@PathVariable Long id);

    @GetMapping(path = "/api/doctor", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> list();

    @PutMapping(path = "/api/doctor", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> update(@Valid @RequestBody DoctorUpdateDto updateDto);

    @PutMapping(path = "/api/doctor/deleted-hospital/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateDeletedHospital(@PathVariable Long id);
}
