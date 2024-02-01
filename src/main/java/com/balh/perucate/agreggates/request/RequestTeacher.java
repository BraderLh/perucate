package com.balh.perucate.agreggates.request;

import lombok.Getter;
import lombok.Setter;

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
    private int educationEntityId;
}
