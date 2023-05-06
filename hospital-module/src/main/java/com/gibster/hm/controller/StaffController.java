package com.gibster.hm.controller;

import com.gibster.hm.feigns.DoctorFeign;
import com.gibster.hm.feigns.NurseFeign;
import com.gibster.hm.feigns.PatientFeign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class StaffController {

  private final DoctorFeign doctorFeign;
  private final NurseFeign nurseFeign;
  private final PatientFeign patientFeign;

  @PostMapping(path = "/doctor/{doctorId}/appoint/for/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> appointDoctorForThePatient(@PathVariable Long doctorId, @PathVariable Long patientId) {
    ResponseEntity<String> doctorResponse = doctorFeign.appointPatientForDoctor(doctorId, patientId);
    ResponseEntity<String> patientResponse = patientFeign.appointDoctorForPatient(patientId, doctorId);

    if (HttpStatus.OK.equals(doctorResponse.getStatusCode()) && HttpStatus.OK.equals(patientResponse.getStatusCode())) {
      return ResponseEntity.ok("Doctor was successfully appointed for the patient");
    } else {
      return ResponseEntity.internalServerError().body("Doctor was successfully appointed for the patient");
    }
  }

  @PostMapping(path = "/nurse/{nurseId}/appoint/for/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> appointNurseForThePatient(@PathVariable Long nurseId, @PathVariable Long patientId) {
    ResponseEntity<String> doctorResponse = nurseFeign.appointPatientForNurse(nurseId, patientId);
    ResponseEntity<String> patientResponse = patientFeign.appointNurseForPatient(patientId, nurseId);

    if (HttpStatus.OK.equals(doctorResponse.getStatusCode()) && HttpStatus.OK.equals(patientResponse.getStatusCode())) {
      return ResponseEntity.ok("Nurse was successfully appointed for the patient");
    } else {
      return ResponseEntity.internalServerError().body("Doctor was successfully appointed for the patient");
    }
  }
}
