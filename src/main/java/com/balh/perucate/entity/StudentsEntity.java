package com.balh.perucate.entity;

import com.balh.perucate.entity.common.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NamedQuery(name = "StudentsEntity.findByNumDoc", query = "select a from StudentsEntity a where a.numDocument=:doc")
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
    @JoinColumn(name = "document_type_id_document_type", referencedColumnName = "id_documents_type", nullable = false)
    private DocumentsTypeEntity documentsTypeEntity;

    @OneToMany(mappedBy = "studentsEntityEnrolled", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonManagedReference(value = "students-enrollments")
    private Set<EnrollmentsEntity> enrollmentsStudent = new HashSet<>();

    @OneToMany(mappedBy = "studentsEntity", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<ScoreEntity> scoreStudentEntitySet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_classroom", referencedColumnName = "id_classroom")
    private ClassroomEntity classroomEntity;
}
