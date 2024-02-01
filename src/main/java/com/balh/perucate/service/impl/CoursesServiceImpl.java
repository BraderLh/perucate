package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestCourse;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.CourseEntity;
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
            CourseEntity courseEntity = getEntityCreate(requestCourse);
            coursesRepository.save(courseEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(courseEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteCourse(Integer id) {
        if (coursesRepository.existsById(id)) {
            Optional<CourseEntity> courseEntityToDelete = coursesRepository.findById(id);
            if (courseEntityToDelete.isPresent()) {
                CourseEntity courseEntityDeleted = getEntityDelete(courseEntityToDelete.get());
                coursesRepository.save(courseEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(courseEntityDeleted));
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
            Optional<CourseEntity> courseEntityToFind = coursesRepository.findById(id);
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
        List<CourseEntity> courseEntityList = coursesRepository.findAll();
        if (!courseEntityList.isEmpty()) {
            courseEntityList.sort(Comparator.comparingInt(CourseEntity::getIdCourse));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(courseEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private CourseEntity getEntityCreate(RequestCourse requestCourse) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCode(requestCourse.getCode());
        courseEntity.setName(requestCourse.getName());
        courseEntity.setBibliography(requestCourse.getBibliography());
        courseEntity.setDescription(requestCourse.getDescription());
        courseEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        courseEntity.setStatus(Constants.STATUS_ACTIVE);
        courseEntity.setUserCreate(Constants.AUDIT_ADMIN);
        return courseEntity;
    }

    private CourseEntity getEntityDelete(CourseEntity courseEntity) {
        courseEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        courseEntity.setStatus(Constants.STATUS_INACTIVE);
        courseEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return courseEntity;
    }

    private CourseEntity getEntityUpdate(RequestCourse requestCourse, CourseEntity courseEntity) {
        courseEntity.setCode(requestCourse.getCode());
        courseEntity.setName(requestCourse.getName());
        courseEntity.setBibliography(requestCourse.getBibliography());
        courseEntity.setDescription(requestCourse.getDescription());
        courseEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        courseEntity.setUserModify(Constants.AUDIT_ADMIN);
        return courseEntity;
    }

    @Override
    public ResponseBase updateCourse(Integer id, RequestCourse requestCourse) {
        boolean validationInput = courseValidation.validateInput(requestCourse);
        if (validationInput) {
            if (coursesRepository.existsById(id)) {
                Optional<CourseEntity> courseEntityToUpdate = coursesRepository.findById(id);
                if (courseEntityToUpdate.isPresent()) {
                    CourseEntity courseEntityUpdated = getEntityUpdate(requestCourse, courseEntityToUpdate.get());
                    coursesRepository.save(courseEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(courseEntityUpdated));
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
