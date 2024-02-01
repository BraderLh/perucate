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
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentsEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    private Integer idStudent;

    @Column(name = "num_document", length = 8, nullable = false)
    private String numDocument;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "surname", length = 200, nullable = false)
    private String surname;

    @Column(name = "telephone", length = 11, nullable = false)
    private String telephone;

    @Column(name = "age", nullable = false)
    private int age;

    @ManyToOne
    @MapsId("classroomId")
    @JoinColumn(name = "classroom_id_classroom", referencedColumnName = "id_classroom", nullable = false)
    private ClassroomEntity classroomEntity;

    @ManyToOne
    @JoinColumn(name = "document_type_id_document_type", referencedColumnName = "id_documents_type", nullable = false)
    private DocumentsTypeEntity documentsTypeEntity;

    @OneToMany(mappedBy = "studentEntityEnrolled",cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<EnrollmentsEntity> enrollmentsStudent = new HashSet<>();

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<ScoreEntity> scoreStudentEntitySet = new HashSet<>();
}
