package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestEnrollment;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface EnrollmentsService {
    ResponseBase createEnrollment(RequestEnrollment requestEnrollment);
    ResponseBase deleteEnrollment(Integer id);
    ResponseBase findOneEnrollment(Integer id);
    ResponseBase findAllEnrollments();
    ResponseBase updateEnrollment(Integer id, RequestEnrollment requestEnrollment);
}
