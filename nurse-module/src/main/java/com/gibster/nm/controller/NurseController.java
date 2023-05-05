package com.gibster.nm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibster.nm.service.NurseService;
import com.gibster.repo.commons.exceptions.BusinessLayerException;

import com.gibster.repo.nm.dto.NurseDto;
import com.gibster.repo.nm.dto.NurseUpdateDto;
import com.gibster.repo.nm.model.Nurse;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nurse")
@RequiredArgsConstructor
public class NurseController {
  private final NurseService service;
  private final ObjectMapper objectMapper;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> create(@Valid @RequestBody Nurse nurse) {
    try {
      NurseDto nurseDto = service.create(nurse);
      return Objects.isNull(nurseDto) ?
          ResponseEntity.internalServerError().build() :
          ResponseEntity.ok(objectMapper.writeValueAsString(nurseDto));
    } catch (JsonProcessingException | BusinessLayerException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> get(@PathVariable Long id) {
    try {
      NurseDto nurseDto = service.get(id);
      return Objects.isNull(nurseDto) ?
          ResponseEntity.notFound().build() :
          ResponseEntity.ok(objectMapper.writeValueAsString(nurseDto));
    } catch (JsonProcessingException | BusinessLayerException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> delete(@PathVariable Long id) {
    try {
      boolean deleteStatus = service.delete(id);
      return BooleanUtils.isFalse(deleteStatus) ?
          ResponseEntity.internalServerError().build() :
          ResponseEntity.ok("Entity with: " + id + " deleted.");
    } catch (BusinessLayerException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> list() {
    try {
      List<NurseDto> list = service.list();
      return list.isEmpty() ?
          ResponseEntity.internalServerError().body("No Records found.") :
          ResponseEntity.ok(objectMapper.writeValueAsString(list));
    } catch (JsonProcessingException | BusinessLayerException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> update(@Valid @RequestBody NurseUpdateDto updateDto) {
    try {
      NurseDto nurse = service.update(updateDto.getId(), updateDto.getHospitalId());
      return Objects.isNull(nurse) ?
          ResponseEntity.internalServerError().body("No nurse found with specified Id.") :
          ResponseEntity.ok(objectMapper.writeValueAsString(nurse));
    } catch (JsonProcessingException | BusinessLayerException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PutMapping(path = "/deleted-hospital/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateDeletedHospital(@PathVariable Long id) {
    try {
      boolean serviceResult = service.updateDeletedHospital(id);
      return BooleanUtils.isFalse(serviceResult) ?
          ResponseEntity.internalServerError().build() :
          ResponseEntity.ok(String.valueOf(serviceResult));
    } catch (BusinessLayerException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
