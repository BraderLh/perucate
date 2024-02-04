package com.balh.perucate.agreggates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCancelEnrollment {
    private int enrollmentsEntityId;
    private String cancellationReason;
}
