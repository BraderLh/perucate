package com.balh.perucate.util.validations;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestTeacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherValidation {
    public boolean isNullOrEmpty(String data) {
        return data != null && !data.isEmpty() && !data.isBlank();
    }

    public boolean validateInput(RequestTeacher requestTeacher) {
        if (requestTeacher == null) {
            return false;
        }

        if (requestTeacher.getAge()<0) {
            return false;
        }

        if (requestTeacher.getNumDocument().length() != Constants.LENGTH_DNI) {
            return false;
        }

        if (requestTeacher.getTelephone().length() != Constants.LENGTH_PHONE_NUMBER) {
            return false;
        }

        return isNullOrEmpty(requestTeacher.getName()) || isNullOrEmpty(requestTeacher.getSurname()) ||
                isNullOrEmpty(requestTeacher.getNumDocument()) || isNullOrEmpty(requestTeacher.getAddress()) ||
                isNullOrEmpty(String.valueOf(requestTeacher.getAge()));
    }
}
