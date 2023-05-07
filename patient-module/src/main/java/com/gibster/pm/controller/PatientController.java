package com.gibster.pm.controller;

import com.gibster.pm.service.PatientService;
import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.model.Doctor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibster.repo.nm.model.Nurse;
import com.gibster.repo.pm.dto.DoctorAppointmentDto;
import com.gibster.repo.pm.dto.NurseAppointmentDto;
import com.gibster.repo.pm.dto.PatientDto;
import com.gibster.repo.pm.dto.PatientUpdateDto;
import com.gibster.repo.pm.model.Diagnosis;
import com.gibster.repo.pm.model.Patient;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService service;
    private final ObjectMapper objectMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody Patient patient) {
        try {
            PatientDto patientDto = service.create(patient);
            return Objects.isNull(patientDto) ?
                    ResponseEntity.internalServerError().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable Long id) {
        try {
            PatientDto patientDto = service.get(id);
            return Objects.isNull(patientDto) ?
                    ResponseEntity.notFound().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> list() {
        try {
            List<PatientDto> list = service.list();
            return BooleanUtils.isTrue(list.isEmpty()) ?
                    ResponseEntity.notFound().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            boolean serviceResult = service.delete(id);
            return BooleanUtils.isFalse(serviceResult) ?
                    ResponseEntity.notFound().build() :
                    ResponseEntity.ok("Patient with: " + id + " deleted.");
        } catch (BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@Valid @RequestBody PatientUpdateDto dto) {
        try {
            PatientDto patientDto = service.update(dto);
            return Objects.isNull(patientDto) ?
                    ResponseEntity.internalServerError().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/updated-doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDoctorInformation(@PathVariable Long id, @RequestBody Doctor doctor) {
        try {
            PatientDto patientDto = service.updateDoctorInfo(id, doctor);
            return Objects.isNull(patientDto) ?
                    ResponseEntity.internalServerError().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/deleted-doctor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDeletedDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        try {
            PatientDto patientDto = service.updateDeletedDoctor(id, doctor);
            return Objects.isNull(patientDto) ?
                    ResponseEntity.internalServerError().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/updated-nurse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateNurseInformation(@PathVariable Long id, @RequestBody Nurse nurse) {
        try {
            PatientDto patientDto = service.updateNurseInfo(id, nurse);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/deleted-nurse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDeletedNurse(@PathVariable Long id, @RequestBody Nurse nurse) {
        try {
            PatientDto patientDto = service.updateDeletedNurse(id, nurse);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/{patientId}/appoint/doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> appointDoctorForPatient(@PathVariable Long patientId, @PathVariable Long doctorId) {
        try {
            PatientDto patientDto = service.appointDoctorForPatient(patientId, doctorId);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/{patientId}/appoint/nurse/{nurseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> appointNurseForPatient(@PathVariable Long patientId, @PathVariable Long nurseId) {
        try {
            PatientDto patientDto = service.appointNurseForPatient(patientId, nurseId);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/{patientId}/appoint/diagnosis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> appointDiagnosisForPatient(@PathVariable Long patientId,
                                                                 @RequestBody Diagnosis diagnosis) {
        try {
            PatientDto patientDto = service.appointDiagnosisForPatient(patientId, diagnosis);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(patientDto);
        } catch (BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(PatientDto.builder().build());
        }
    }


    @PostMapping(path = "/{patientId}/nurse/assign/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> assignAnAppointmentByNurse(@PathVariable Long patientId,
                                                             @RequestBody NurseAppointmentDto nurseAppointmentDto) {
        try {
            PatientDto patientDto = service.assignAnAppointmentByNurse(patientId, nurseAppointmentDto);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(patientDto);
        } catch (BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(PatientDto.builder().build());
        }
    }

    @PostMapping(path = "/{patientId}/doctor/assign/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> assignAnAppointmentByDoctor(@PathVariable Long patientId,
                                                             @RequestBody DoctorAppointmentDto doctorAppointmentDto) {
        try {
            PatientDto patientDto = service.assignAnAppointmentByDoctor(patientId, doctorAppointmentDto);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(patientDto);
        } catch (BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(PatientDto.builder().build());
        }
    }

    @PostMapping(path = "/{patientId}/discharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> dischargePatientByDoctor(@PathVariable Long patientId) {
        try {
            PatientDto patientDto = service.dischargePatientByDoctor(patientId);
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(patientDto);
        } catch (BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(PatientDto.builder().build());
        }
    }
}
