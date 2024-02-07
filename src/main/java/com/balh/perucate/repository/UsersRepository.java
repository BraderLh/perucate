package com.balh.perucate.repository;

import com.balh.perucate.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByEmail(@Param("email") String email);
}

