package com.gibster.nm.repository;

import com.gibster.repo.nm.model.Nurse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
  @Query("select n from Nurse n where n.hospitalId=?1")
  List<Nurse> findNursesByHospitalId(Long hospitalId);
}
