package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "UsersEntity.findByEmail", query = "select u from UsersEntity u where u.email=:email")
@Entity
@Getter
@Setter
@Table(name = "users")
public class UsersEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;
}
