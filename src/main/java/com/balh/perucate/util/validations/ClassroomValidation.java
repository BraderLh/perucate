package com.balh.perucate.util.validations;

import com.balh.perucate.agreggates.request.RequestClassroom;
import org.springframework.stereotype.Component;

@Component
public class ClassroomValidation {
    public boolean isNullOrEmpty(String data) {
        return data != null && !data.isEmpty() && !data.isBlank();
    }

    public boolean validateInput(RequestClassroom requestClassroom) {
        if (requestClassroom == null) {
            return false;
        }
        return isNullOrEmpty(requestClassroom.getClassTitle()) ||
                isNullOrEmpty(String.valueOf(requestClassroom.getCourseEntityId())) ||
                isNullOrEmpty(String.valueOf(requestClassroom.getTeachersEntityId()));
    }
}
