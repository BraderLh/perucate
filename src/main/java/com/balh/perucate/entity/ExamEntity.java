package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
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
@Table(name = "exam")
public class ExamEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exam")
    private Integer examId;

    @Column(name = "exam_file", nullable = false)
    private String examFile;

    @Column(name = "resolution", nullable = false)
    private String resolution;

    @Column(name = "exam_date", nullable = false)
    private Timestamp examDate;

    @Column(name = "time", nullable = false)
    private int time;

    @OneToOne
    @JoinColumn(name = "id_course", referencedColumnName = "id_course", nullable = false)
    private CoursesEntity coursesEntity;
}
