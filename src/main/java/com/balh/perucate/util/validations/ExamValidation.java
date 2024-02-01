package com.balh.perucate.util.validations;

import com.balh.perucate.agreggates.request.RequestExam;
import org.springframework.stereotype.Component;

@Component
public class ExamValidation {
    public boolean isNullOrEmpty(String data) {
        return data != null && !data.isEmpty() && !data.isBlank();
    }

    public boolean validateInput(RequestExam requestExam) {
        if (requestExam == null) {
            return false;
        }
        return isNullOrEmpty(requestExam.getExamFile()) || isNullOrEmpty(requestExam.getResolution()) ||
                isNullOrEmpty(String.valueOf(requestExam.getCourseEntityId())) || isNullOrEmpty(String.valueOf(requestExam.getTime()));
    }
}
