package com.balh.perucate.agreggates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestScore {
    int score;
    private int examEntityId;
    private int studentsEntityId;
    private int teachersEntityId;
}
