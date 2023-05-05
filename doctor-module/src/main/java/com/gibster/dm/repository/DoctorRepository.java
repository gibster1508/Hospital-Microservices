package com.gibster.dm.repository;

import com.gibster.repo.dm.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("select d from Doctor d where d.hospitalId=?1")
    List<Doctor> findDoctorsByHospitalId(Long hospitalId);
}
