package com.balh.perucate.agreggates.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RequestCourse {
    private String code;
    private String name;
    private String bibliography;
    private String description;
}
