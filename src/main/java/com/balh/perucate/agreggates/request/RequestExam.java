package com.balh.perucate.agreggates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestExam {
    private String examFile;
    private String resolution;
    private int time;
    private int courseEntityId;
}
