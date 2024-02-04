package com.balh.perucate.agreggates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestClassroom {
    private String classTitle;
    private int courseEntityId;
    private int teachersEntityId;
}
