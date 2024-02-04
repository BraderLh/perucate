package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassroomEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classroom")
    private Integer classroomId;

    @Column(name = "class_title", nullable = false)
    private String classTitle;

    @Column(name = "class_date", nullable = false)
    private Timestamp classDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course", referencedColumnName = "id_course")
    private CoursesEntity coursesEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_teacher", referencedColumnName = "id_teacher")
    private TeachersEntity teachersEntity;

    @OneToMany(mappedBy = "classroomEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "id_student", referencedColumnName = "id_student")
    private Set<StudentsEntity> studentsEntitySet = new HashSet<>();

}
