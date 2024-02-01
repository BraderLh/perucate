package com.balh.perucate.util.validations;

import com.balh.perucate.agreggates.request.RequestCourse;
import org.springframework.stereotype.Component;

@Component
public class CourseValidation {
    public boolean isNullOrEmpty(String data) {
        return data != null && !data.isEmpty() && !data.isBlank();
    }

    public boolean validateInput(RequestCourse requestCourse) {
        if (requestCourse == null) {
            return false;
        }
        return isNullOrEmpty(requestCourse.getCode()) || isNullOrEmpty(requestCourse.getName()) ||
                isNullOrEmpty(requestCourse.getDescription()) || isNullOrEmpty(requestCourse.getBibliography());
    }
}
