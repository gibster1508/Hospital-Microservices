package com.gibster.nm.service;

import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.nm.dto.NurseDto;
import com.gibster.repo.nm.model.Nurse;
import java.util.List;

public interface NurseService {
  NurseDto create(Nurse nurse) throws BusinessLayerException;

  NurseDto get(Long id) throws BusinessLayerException;

  NurseDto update(Long id, Long hospitalId) throws BusinessLayerException;

  boolean updateDeletedHospital(Long hospitalId) throws BusinessLayerException;

  boolean delete(Long nurseId) throws BusinessLayerException;

  List<NurseDto> list() throws BusinessLayerException;
}
