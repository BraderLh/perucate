package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Table(name = "documents_type")
@Getter
@Setter
@NamedQuery(name = "DocumentsTypeEntity.findByCodType", query = "select a from DocumentsTypeEntity a where a.codType=:codType")
@NoArgsConstructor
public class DocumentsTypeEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documents_type")
    private Integer idDocumentsType;

    @Column(name = "cod_type", nullable = false)
    private String codType;

    @Column(name = "desc_type", nullable = false)
    private String descType;
}
