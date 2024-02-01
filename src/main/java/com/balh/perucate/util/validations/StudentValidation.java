package com.balh.perucate.util.validations;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestStudent;
import org.springframework.stereotype.Component;

@Component
public class StudentValidation {
    public boolean isNullOrEmpty(String data) {
        return data != null && !data.isEmpty() && !data.isBlank();
    }

    public boolean validateInput(RequestStudent requestStudent) {
        if (requestStudent == null) {
            return false;
        }

        if (requestStudent.getNumDocument().length() != Constants.LENGTH_DNI) {
            return false;
        }

        if (requestStudent.getAge()<=0) {
            return false;
        }

        if (requestStudent.getTelephone().length() != Constants.LENGTH_PHONE_NUMBER) {
            return false;
        }

        return isNullOrEmpty(requestStudent.getName()) || isNullOrEmpty(requestStudent.getSurname()) ||
                isNullOrEmpty(requestStudent.getNumDocument()) || isNullOrEmpty(requestStudent.getTelephone()) ||
                isNullOrEmpty(String.valueOf(requestStudent.getAge()));
    }
}
