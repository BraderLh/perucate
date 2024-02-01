package com.balh.perucate.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Audit {
    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @Column(name = "date_create")
    private Timestamp dateCreate;

    @Column(name = "date_delete")
    private Timestamp dateDelete;

    @Column(name = "date_modif")
    private Timestamp dateModify;

    @Column(name = "user_create", length = 50)
    private String userCreate;

    @Column(name = "user_delete", length = 50)
    private String userDelete;

    @Column(name = "user_modif", length = 50)
    private String userModify;
}
