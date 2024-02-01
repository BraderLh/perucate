package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    private Integer idCourse;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "bibliography", length = 250, nullable = false)
    private String bibliography;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "courseEntityEnrolled", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<EnrollmentsEntity> enrollmentsCourse = new HashSet<>();
}
