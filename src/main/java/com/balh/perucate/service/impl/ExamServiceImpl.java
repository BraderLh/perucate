package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestExam;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.CoursesEntity;
import com.balh.perucate.entity.ExamEntity;
import com.balh.perucate.repository.CoursesRepository;
import com.balh.perucate.repository.ExamRepository;
import com.balh.perucate.service.ExamService;
import com.balh.perucate.util.validations.ExamValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    private final CoursesRepository coursesRepository;
    private final ExamRepository examRepository;
    private final ExamValidation examValidation;

    public ExamServiceImpl(CoursesRepository coursesRepository, ExamRepository examRepository, ExamValidation examValidation) {
        this.coursesRepository = coursesRepository;
        this.examRepository = examRepository;
        this.examValidation = examValidation;
    }

    @Override
    public ResponseBase createExam(RequestExam requestExam) {
        boolean validateInput = examValidation.validateInput(requestExam);
        if (validateInput) {
            ExamEntity examEntity = getEntityCreate(requestExam);
            examRepository.save(examEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(examEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteExam(Integer id) {
        if (examRepository.existsById(id)) {
            Optional<ExamEntity> examEntityToDelete = examRepository.findById(id);
            if (examEntityToDelete.isPresent()) {
                ExamEntity examEntityDeleted = getEntityDelete(examEntityToDelete.get());
                examRepository.save(examEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(examEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneExam(Integer id) {
        if (examRepository.existsById(id)) {
            Optional<ExamEntity> examEntityToFind = examRepository.findById(id);
            if(examEntityToFind.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, examEntityToFind);
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllExams() {
        List<ExamEntity> examEntityList = examRepository.findAll();
        if (!examEntityList.isEmpty()) {
            examEntityList.sort(Comparator.comparingInt(ExamEntity::getExamId));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(examEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private ExamEntity getEntityCreate(RequestExam requestExam) {
        ExamEntity examEntity = new ExamEntity();
        examEntity.setExamFile(requestExam.getExamFile());
        examEntity.setResolution(requestExam.getResolution());
        examEntity.setExamDate(new Timestamp(System.currentTimeMillis()));
        examEntity.setTime(requestExam.getTime());
        examEntity.setCoursesEntity(getCourse(requestExam.getCourseEntityId()));
        examEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        examEntity.setStatus(Constants.STATUS_ACTIVE);
        examEntity.setUserCreate(Constants.AUDIT_ADMIN);
        return examEntity;
    }

    private CoursesEntity getCourse(int courseEntityId) {
        if (coursesRepository.existsById(courseEntityId)) {
            Optional<CoursesEntity> courseEntity = coursesRepository.findById(courseEntityId);
            return courseEntity.orElse(null);
        }
        else {
            return null;
        }
    }

    private ExamEntity getEntityDelete(ExamEntity examEntity) {
        examEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        examEntity.setStatus(Constants.STATUS_INACTIVE);
        examEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return examEntity;
    }

    private ExamEntity getEntityUpdate(RequestExam requestExam, ExamEntity examEntity) {
        examEntity.setExamFile(requestExam.getExamFile());
        examEntity.setResolution(requestExam.getResolution());
        examEntity.setExamDate(new Timestamp(System.currentTimeMillis()));
        examEntity.setTime(requestExam.getTime());
        examEntity.setCoursesEntity(getCourse(requestExam.getCourseEntityId()));
        examEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        examEntity.setUserModify(Constants.AUDIT_ADMIN);
        return examEntity;
    }

    @Override
    public ResponseBase updateExam(Integer id, RequestExam requestExam) {
        boolean validationInput = examValidation.validateInput(requestExam);
        if (validationInput) {
            if (examRepository.existsById(id)) {
                Optional<ExamEntity> examEntityToUpdate = examRepository.findById(id);
                if (examEntityToUpdate.isPresent()) {
                    ExamEntity examEntityUpdated = getEntityUpdate(requestExam, examEntityToUpdate.get());
                    examRepository.save(examEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(examEntityUpdated));
                } else {
                    return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_UPDATE, Optional.empty());
                }
            } else  {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
            }
        }  else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }
}
