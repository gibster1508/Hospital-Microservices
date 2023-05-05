package com.gibster.pm.feigns;

import com.gibster.repo.nm.dto.NurseUpdateDto;
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

@FeignClient(name = "nurse-module")
public interface NurseFeign {
  @PostMapping(path = "/api/nurse", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> create(@Valid @RequestBody Doctor doctor);

  @GetMapping(path = "/api/nurse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> get(@PathVariable Long id);

  @DeleteMapping(path = "/api/nurse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> delete(@PathVariable Long id);

  @GetMapping(path = "/api/nurse", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> list();

  @PutMapping(path = "/api/nurse", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> update(@Valid @RequestBody NurseUpdateDto updateDto);

  @PutMapping(path = "/api/nurse/deleted-hospital/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> updateDeletedHospital(@PathVariable Long id);
}