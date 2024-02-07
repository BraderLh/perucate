package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_score")
    private Integer idScore;

    @Column(name = "score", nullable = false)
    int score;

    @OneToOne
    @JoinColumn(name = "exam_id_exam", referencedColumnName = "id_exam", nullable = false)
    private ExamEntity examEntity;

    @ManyToOne
    @JoinColumn(name = "student_id_student", referencedColumnName = "id_student", nullable = false)
    private StudentsEntity studentsEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id_teacher", referencedColumnName = "id_teacher", nullable = false)
    @JsonBackReference(value = "teachers-scores")
    private TeachersEntity teachersEntity;
}
