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
@Setter
@NoArgsConstructor
@Table(name = "cancel_enrollments")
public class CancelEnrolmentEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancel_enrollments")
    private Integer cancelEnrollmentsId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enrollments_id_enrollment", referencedColumnName = "id_enrollment")
    private EnrollmentsEntity enrollmentsEntity;

    @Column(name = "surname", length = 200)
    private String cancellationReason;

    @Column(name = "cancel_date", nullable = false)
    private Timestamp cancelDate;

    @Column(name = "user_cancel", length = 50)
    private String userCancel;
}
