package com.balh.perucate.agreggates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStudent {
    private String numDocument;
    private String name;
    private String surname;
    private String telephone;
    private int age;
    private int documentTypeEntityId;
}
