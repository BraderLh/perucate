package com.balh.perucate.util.validations;

import com.balh.perucate.agreggates.request.RequestScore;
import org.springframework.stereotype.Component;

@Component
public class ScoreValidation {
    public boolean isNullOrEmpty(String data) {
        return data != null && !data.isEmpty() && !data.isBlank();
    }

    public boolean validateInput(RequestScore requestScore) {
        if (requestScore == null) {
            return false;
        }
        return isNullOrEmpty(String.valueOf(requestScore.getScore())) ||
                isNullOrEmpty(String.valueOf(requestScore.getCourseEntityId())) ||
                isNullOrEmpty(String.valueOf(requestScore.getStudentEntityId())) ||
                isNullOrEmpty(String.valueOf(requestScore.getTeacherEntityId()));
    }
}
