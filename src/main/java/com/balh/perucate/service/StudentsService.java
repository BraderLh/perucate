package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestStudent;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface StudentsService {
    ResponseBase createStudent(RequestStudent requestStudent);
    ResponseBase deleteStudent(Integer id);
    ResponseBase findOneStudent(String doc);
    ResponseBase findAllStudents();
    ResponseBase updateStudent(Integer id, RequestStudent requestStudent);
}
