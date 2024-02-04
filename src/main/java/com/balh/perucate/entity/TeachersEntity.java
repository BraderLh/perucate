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
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeachersEntity extends Audit {
    @Id
    @Column(name = "id_teacher")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTeacher;

    @Column(name = "num_document", length = 8, nullable = false)
    private String numDocument;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "surname", length = 200, nullable = false)
    private String surname;

    @Column(name = "telephone", length = 11, nullable = false)
    private String telephone;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "career", length = 200, nullable = false)
    private String career;

    @Column(name = "degree", length = 200, nullable = false)
    private String degree;

    @Column(name = "specialization", length = 200, nullable = false)
    private String specialization;

    @Column(name = "university_name", length = 200, nullable = false)
    private String universityName;

    @Column(name = "age", nullable = false)
    private int age;

    @ManyToOne
    @JoinColumn(name = "document_type_id_document_type", referencedColumnName = "id_documents_type", nullable = false)
    private DocumentsTypeEntity documentsTypeEntity;
    
    @OneToMany(mappedBy = "teachersEntity", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<ScoreEntity> scoreTeacherEntitySet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "id_teacher"),
            inverseJoinColumns = @JoinColumn(name = "id_course")
    )
    private Set<CoursesEntity> courseEntitiesSet = new HashSet<>();
}