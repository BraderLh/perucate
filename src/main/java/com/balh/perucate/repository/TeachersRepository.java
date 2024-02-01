package com.balh.perucate.repository;

import com.balh.perucate.entity.TeachersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachersRepository extends JpaRepository<TeachersEntity, Integer> {
}
