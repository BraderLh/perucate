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

        if (requestScore.getScore()<0 || requestScore.getScore()>20) {
            return false;
        }

        return isNullOrEmpty(String.valueOf(requestScore.getScore())) ||
                isNullOrEmpty(String.valueOf(requestScore.getExamEntity())) ||
                isNullOrEmpty(String.valueOf(requestScore.getStudentsEntityId())) ||
                isNullOrEmpty(String.valueOf(requestScore.getTeachersEntityId()));
    }
}
