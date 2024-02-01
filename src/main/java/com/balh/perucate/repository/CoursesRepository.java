package com.balh.perucate.repository;

import com.balh.perucate.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<CourseEntity, Integer> {
}
