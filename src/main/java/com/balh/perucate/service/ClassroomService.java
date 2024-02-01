package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestClassroom;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface ClassroomService {
    ResponseBase createClassroom(RequestClassroom requestClassroom);
    ResponseBase deleteClassroom(Integer id);
    ResponseBase findOneClassroom(Integer id);
    ResponseBase findAllClassrooms();
    ResponseBase updateClassroom(Integer id, RequestClassroom requestClassroom);
}
