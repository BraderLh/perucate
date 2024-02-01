package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestTeacher;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface TeachersService {
    ResponseBase createTeacher(RequestTeacher requestTeacher);
    ResponseBase deleteTeacher(Integer id);
    ResponseBase findOneTeacher(Integer id);
    ResponseBase findAllTeachers();
    ResponseBase updateTeacher(Integer id, RequestTeacher requestTeacher);
}
