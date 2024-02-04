package com.balh.perucate.util.validations;

import com.balh.perucate.agreggates.request.RequestCancelEnrollment;
import com.balh.perucate.agreggates.request.RequestEnrollment;
import com.balh.perucate.agreggates.request.RequestScore;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentValidation {
    public boolean isNullOrEmpty(String data) {
        return data != null && !data.isEmpty() && !data.isBlank();
    }

    public boolean validateInput(RequestEnrollment requestEnrollment) {
        if (requestEnrollment == null) {
            return false;
        }
        return isNullOrEmpty(String.valueOf(requestEnrollment.getCoursesEntityEnrolledId())) ||
                isNullOrEmpty(String.valueOf(requestEnrollment.getStudentsEntityEnrolledId()));
    }

    public boolean validateCancelInput(RequestCancelEnrollment requestCancelEnrollment) {
        if (requestCancelEnrollment == null) {
            return false;
        }
        return isNullOrEmpty(String.valueOf(requestCancelEnrollment.getEnrollmentsEntityId())) ||
                isNullOrEmpty(String.valueOf(requestCancelEnrollment.getCancellationReason()));
    }
}
