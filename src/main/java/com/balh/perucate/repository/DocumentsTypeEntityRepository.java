package com.balh.perucate.repository;

import com.balh.perucate.entity.DocumentsTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsTypeEntityRepository extends JpaRepository<DocumentsTypeEntity, Integer> {
    DocumentsTypeEntity findByCodType(@Param("codType") String codType);
}
