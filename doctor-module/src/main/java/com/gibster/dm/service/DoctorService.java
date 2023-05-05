package com.gibster.dm.service;

import com.gibster.repo.commons.exceptions.BusinessLayerException;
import com.gibster.repo.dm.dto.DoctorDto;
import com.gibster.repo.dm.model.Doctor;
import java.util.List;

public interface DoctorService {
    DoctorDto create(Doctor doctor) throws BusinessLayerException;

    DoctorDto get(Long id) throws BusinessLayerException;

    DoctorDto update(Long id, Long hospitalId) throws BusinessLayerException;

    boolean updateDeletedHospital(Long hospitalId) throws BusinessLayerException;

    boolean delete(Long doctorId) throws BusinessLayerException;

    List<DoctorDto> list() throws BusinessLayerException;
}
