package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestExam;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface ExamService {
    ResponseBase createExam(RequestExam requestExam);
    ResponseBase deleteExam(Integer id);
    ResponseBase findOneExam(Integer id);
    ResponseBase findAllExams();
    ResponseBase updateExam(Integer id, RequestExam requestExam);
}
