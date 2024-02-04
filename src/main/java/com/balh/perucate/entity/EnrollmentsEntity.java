package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "id_enrollment")
    private Integer enrollmentId;

    @Column(name = "enroll_date", nullable = false)
    private Timestamp enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "course_id_course", referencedColumnName = "id_course", nullable = false)
    @JsonBackReference(value = "courses-enrollments")
    private CoursesEntity coursesEntityEnrolled;

    @ManyToOne
    @JoinColumn(name = "student_id_student", referencedColumnName = "id_student", nullable = false)
    @JsonBackReference(value = "students-enrollments")
    private StudentsEntity studentsEntityEnrolled;
}
