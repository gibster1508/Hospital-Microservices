package com.gibster.dm.controller;

import com.gibster.dm.feigns.NurseFeign;
import com.gibster.dm.feigns.PatientFeign;
import com.gibster.dm.service.DoctorService;
import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.dto.DoctorDto;
import com.gibster.repo.dm.dto.DoctorUpdateDto;
import com.gibster.repo.dm.model.Doctor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibster.repo.pm.dto.DoctorAppointmentDto;
import com.gibster.repo.pm.dto.PatientDto;
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
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService service;
    private final ObjectMapper objectMapper;
    private final PatientFeign patientFeign;
    private final NurseFeign nurseFeign;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody Doctor doctor) {
        try {
            DoctorDto doctorDto = service.create(doctor);
            return Objects.isNull(doctorDto) ?
                    ResponseEntity.internalServerError().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(doctorDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable Long id) {
        try {
            DoctorDto doctorDto = service.get(id);
            return Objects.isNull(doctorDto) ?
                    ResponseEntity.notFound().build() :
                    ResponseEntity.ok(objectMapper.writeValueAsString(doctorDto));
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
            List<DoctorDto> list = service.list();
            return list.isEmpty() ?
                    ResponseEntity.internalServerError().body("No Records found.") :
                    ResponseEntity.ok(objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@Valid @RequestBody DoctorUpdateDto updateDto) {
        try {
            DoctorDto doctor = service.update(updateDto.getId(), updateDto.getHospitalId());
            return Objects.isNull(doctor) ?
                    ResponseEntity.internalServerError().body("No doctor found with specified Id.") :
                    ResponseEntity.ok(objectMapper.writeValueAsString(doctor));
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

    @PutMapping(path = "/updated-patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePatientInformation(@PathVariable Long id, @RequestBody Patient patient) {
        try {
            DoctorDto doctorDto = service.updatePatientInfo(id, patient);
            return Objects.isNull(doctorDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(doctorDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/deleted-patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDeletedPatient(@PathVariable Long id, @RequestBody Patient patient) {
        try {
            DoctorDto doctorDto = service.updateDeletedPatient(id, patient);
            return Objects.isNull(doctorDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(doctorDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/{doctorId}/appoint/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> appointPatientForDoctor(@PathVariable Long doctorId, @PathVariable Long patientId){
        try {
            DoctorDto doctorDto = service.appointPatientForDoctor(doctorId, patientId);
            return Objects.isNull(doctorDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(doctorDto));
        } catch (JsonProcessingException | BusinessLayerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/appoint/diagnosis/for/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> appointDiagnosisForPatient(@PathVariable Long patientId, @RequestBody
    Diagnosis diagnosis){
        try {
            PatientDto patientDto = patientFeign.appointDiagnosisForPatient(patientId, diagnosis).getBody();
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/assign/appointment/for/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> assignAnAppointmentByDoctor(@PathVariable Long patientId,
                                                           @RequestBody DoctorAppointmentDto doctorAppointmentDto){
        try {
            PatientDto patientDto = patientFeign.assignAnAppointmentByDoctor(patientId, doctorAppointmentDto).getBody();
            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/discharge/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> dischargePatientByDoctor(@PathVariable Long patientId, @RequestBody Diagnosis finalDiagnosis){
        try {
            PatientDto patientDto = patientFeign.dischargePatientByDoctor(patientId).getBody();

            Long doctorId = Objects.requireNonNull(patientDto).getDoctors().get(0);
            Long nurseId = Objects.requireNonNull(patientDto).getNurses().get(0);

            patientFeign.appointDiagnosisForPatient(patientId, finalDiagnosis);
            service.updateDischargedPatient(doctorId, patientId);
            nurseFeign.updateDischargedPatient(nurseId, patientId);

            return Objects.isNull(patientDto) ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(objectMapper.writeValueAsString(patientDto));
        } catch (BusinessLayerException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
