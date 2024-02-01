package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "enrollments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentsEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollmentId")
    private Integer enrollmentId;

    @Column(name = "enroll_date", nullable = false)
    private Timestamp enrollmentDate;

    @ManyToOne
    @MapsId("idCourse")
    @JoinColumn(name = "id_course", referencedColumnName = "id_course", nullable = false)
    private CourseEntity courseEntityEnrolled;

    @ManyToOne
    @MapsId("idStudent")
    @JoinColumn(name = "id_student", referencedColumnName = "id_student", nullable = false)
    private StudentsEntity studentsEntityEnrolled;

    @Column(name = "cancelled", length = 1, nullable = false)
    private int cancelled;

    @Column(name = "surname", length = 200)
    private String cancellationReason;
}
