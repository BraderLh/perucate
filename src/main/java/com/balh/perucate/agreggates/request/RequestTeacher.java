package com.balh.perucate.agreggates.request;

import com.balh.perucate.entity.CoursesEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RequestTeacher {
    private String numDocument;
    private String name;
    private String surname;
    private String telephone;
    private String address;
    private String career;
    private String degree;
    private String specialization;
    private String universityName;
    private int age;
    private int documentTypeEntityId;
    private Set<CoursesEntity> idsCourseEntities = new HashSet<>();
}
