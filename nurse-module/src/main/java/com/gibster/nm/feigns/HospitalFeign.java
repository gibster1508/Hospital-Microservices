package com.gibster.nm.feigns;

import com.gibster.repo.hm.dto.HospitalUpdateDto;
import com.gibster.repo.hm.model.Hospital;
import com.gibster.repo.nm.model.Nurse;
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

    @PutMapping(path = "/api/hospital/updated-nurse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateNurseInformation(@PathVariable Long id, @RequestBody Nurse nurse);

    @PutMapping(path = "/api/hospital/deleted-nurse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateDeletedNurse(@PathVariable Long id, @RequestBody Nurse nurse);

}
