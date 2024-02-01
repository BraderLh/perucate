package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestCourse;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface CoursesService {
    ResponseBase createCourse(RequestCourse requestCourse);
    ResponseBase deleteCourse(Integer id);
    ResponseBase findOneCourse(Integer id);
    ResponseBase findAllCourses();
    ResponseBase updateCourse(Integer id, RequestCourse requestCourse);
}
