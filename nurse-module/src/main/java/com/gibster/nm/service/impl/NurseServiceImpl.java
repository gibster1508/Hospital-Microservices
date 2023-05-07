package com.gibster.nm.service.impl;

import com.gibster.nm.feigns.HospitalFeign;
import com.gibster.nm.helper.PopulateHelper;
import com.gibster.nm.repository.NurseRepository;
import com.gibster.nm.service.NurseService;
import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.nm.dto.NurseDto;
import com.gibster.repo.nm.model.Nurse;
import com.gibster.repo.pm.model.Patient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NurseServiceImpl implements NurseService {
  private final NurseRepository repository;
  private final HospitalFeign hospitalFeign;
  @Override
  public NurseDto create(Nurse nurse) throws BusinessLayerException {
    try {
      nurse.setPatients(Collections.emptyList());
      Nurse savedEntity = repository.save(nurse);

      hospitalFeign.updateNurseInformation(savedEntity.getHospitalId(), savedEntity);
      return PopulateHelper.convertToNurseDto(savedEntity);
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public NurseDto get(Long id) throws BusinessLayerException {
    try {
      Nurse nurse = repository.findById(id).orElse(null);
      if (Objects.isNull(nurse))
        return null;
      return PopulateHelper.convertToNurseDto(nurse);
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public NurseDto update(Long id, Long hospitalId) throws BusinessLayerException {
    try {
      Nurse nurse = repository.findById(id).orElse(null);
      if (Objects.isNull(nurse))
        return null;
      hospitalFeign.updateDeletedNurse(nurse.getHospitalId(), nurse);
      nurse.setHospitalId(hospitalId);
      hospitalFeign.updateNurseInformation(hospitalId, nurse);
      return PopulateHelper.convertToNurseDto(repository.save(nurse));
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public boolean updateDeletedHospital(Long hospitalId) throws BusinessLayerException {
    try {
      repository.findNursesByHospitalId(hospitalId).forEach(nurse -> {
        nurse.setHospitalId(null);
        repository.save(nurse);
      });
      return true;
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public boolean delete(Long nurseId) throws BusinessLayerException {
    try {
      Nurse nurse = repository.findById(nurseId).orElse(null);
      if (Objects.isNull(nurse))
        return false;
      repository.delete(nurse);
      ResponseEntity<String> updateResponse = hospitalFeign.updateDeletedNurse(nurse.getHospitalId(), nurse);
      return HttpStatus.OK.equals(updateResponse.getStatusCode());
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public List<NurseDto> list() throws BusinessLayerException {
    try {
      return repository.findAll().stream().map(PopulateHelper::convertToNurseDto).toList();
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public NurseDto updateDeletedPatient(Long id, Patient patient) throws BusinessLayerException {
    try {
      Nurse nurse = repository.findById(id).orElse(null);
      if (Objects.isNull(nurse))
        return null;
      List<Long> patientList = new ArrayList<>(nurse.getPatients());
      patientList.remove(patient.getId());
      nurse.setPatients(patientList);
      return PopulateHelper.convertToNurseDto(repository.save(nurse));
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public NurseDto updateDischargedPatient(Long nurseId, Long patientId) throws BusinessLayerException {
    try {
      Nurse nurse = repository.findById(nurseId).orElse(null);
      if (Objects.isNull(nurse))
        return null;
      List<Long> patientList = new ArrayList<>(nurse.getPatients());
      patientList.remove(patientId);
      nurse.setPatients(patientList);
      return PopulateHelper.convertToNurseDto(repository.save(nurse));
    } catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }

  @Override
  public NurseDto appointPatientForNurse(Long nurseId, Long patientId) throws BusinessLayerException {
    try {
      Nurse nurse = repository.findById(nurseId).orElse(null);
      if (Objects.isNull(nurse))
        return null;
      if (nurse.getPatients().isEmpty()) {
        List<Long> patientList = new ArrayList<>();
        patientList.add(patientId);
        nurse.setPatients(patientList);
      } else {
        List<Long> patientList = new ArrayList<>(nurse.getPatients());
        patientList.add(patientId);
        nurse.setPatients(patientList);
      }
      return PopulateHelper.convertToNurseDto(repository.save(nurse));
    }catch (Exception e) {
      throw new BusinessLayerException(e.getMessage(), e);
    }
  }



}
