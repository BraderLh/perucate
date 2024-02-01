package com.balh.perucate.repository;

import com.balh.perucate.entity.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<StudentsEntity, Integer> {
}
