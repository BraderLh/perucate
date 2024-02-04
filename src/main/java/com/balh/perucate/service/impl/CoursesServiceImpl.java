package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestCourse;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.CoursesEntity;
import com.balh.perucate.repository.CoursesRepository;
import com.balh.perucate.service.CoursesService;
import com.balh.perucate.util.validations.CourseValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CoursesServiceImpl implements CoursesService {
    private final CoursesRepository coursesRepository;
    private final CourseValidation courseValidation;

    public CoursesServiceImpl(CoursesRepository coursesRepository, CourseValidation courseValidation) {
        this.coursesRepository = coursesRepository;
        this.courseValidation = courseValidation;
    }

    @Override
    public ResponseBase createCourse(RequestCourse requestCourse) {
        boolean validateInput = courseValidation.validateInput(requestCourse);
        if (validateInput) {
            CoursesEntity coursesEntity = getEntityCreate(requestCourse);
            coursesRepository.save(coursesEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(coursesEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteCourse(Integer id) {
        if (coursesRepository.existsById(id)) {
            Optional<CoursesEntity> courseEntityToDelete = coursesRepository.findById(id);
            if (courseEntityToDelete.isPresent()) {
                CoursesEntity coursesEntityDeleted = getEntityDelete(courseEntityToDelete.get());
                coursesRepository.save(coursesEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(coursesEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneCourse(Integer id) {
        if (coursesRepository.existsById(id)) {
            Optional<CoursesEntity> courseEntityToFind = coursesRepository.findById(id);
            if(courseEntityToFind.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, courseEntityToFind);
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllCourses() {
        List<CoursesEntity> coursesEntityList = coursesRepository.findAll();
        if (!coursesEntityList.isEmpty()) {
            coursesEntityList.sort(Comparator.comparingInt(CoursesEntity::getIdCourse));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(coursesEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private CoursesEntity getEntityCreate(RequestCourse requestCourse) {
        CoursesEntity coursesEntity = new CoursesEntity();
        coursesEntity.setCode(requestCourse.getCode());
        coursesEntity.setName(requestCourse.getName());
        coursesEntity.setBibliography(requestCourse.getBibliography());
        coursesEntity.setDescription(requestCourse.getDescription());
        coursesEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        coursesEntity.setStatus(Constants.STATUS_ACTIVE);
        coursesEntity.setUserCreate(Constants.AUDIT_ADMIN);
        return coursesEntity;
    }

    private CoursesEntity getEntityDelete(CoursesEntity coursesEntity) {
        coursesEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        coursesEntity.setStatus(Constants.STATUS_INACTIVE);
        coursesEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return coursesEntity;
    }

    private CoursesEntity getEntityUpdate(RequestCourse requestCourse, CoursesEntity coursesEntity) {
        coursesEntity.setCode(requestCourse.getCode());
        coursesEntity.setName(requestCourse.getName());
        coursesEntity.setBibliography(requestCourse.getBibliography());
        coursesEntity.setDescription(requestCourse.getDescription());
        coursesEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        coursesEntity.setUserModify(Constants.AUDIT_ADMIN);
        return coursesEntity;
    }

    @Override
    public ResponseBase updateCourse(Integer id, RequestCourse requestCourse) {
        boolean validationInput = courseValidation.validateInput(requestCourse);
        if (validationInput) {
            if (coursesRepository.existsById(id)) {
                Optional<CoursesEntity> courseEntityToUpdate = coursesRepository.findById(id);
                if (courseEntityToUpdate.isPresent()) {
                    CoursesEntity coursesEntityUpdated = getEntityUpdate(requestCourse, courseEntityToUpdate.get());
                    coursesRepository.save(coursesEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(coursesEntityUpdated));
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
