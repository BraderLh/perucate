package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestClassroom;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.*;
import com.balh.perucate.repository.ClassroomRepository;
import com.balh.perucate.repository.CoursesRepository;
import com.balh.perucate.repository.TeachersRepository;
import com.balh.perucate.service.ClassroomService;
import com.balh.perucate.util.validations.ClassroomValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final ClassroomValidation classroomValidation;
    private final CoursesRepository coursesRepository;
    private final TeachersRepository teachersRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, ClassroomValidation classroomValidation, CoursesRepository coursesRepository, TeachersRepository teachersRepository) {
        this.classroomRepository = classroomRepository;
        this.classroomValidation = classroomValidation;
        this.coursesRepository = coursesRepository;
        this.teachersRepository = teachersRepository;
    }

    @Override
    public ResponseBase createClassroom(RequestClassroom requestClassroom) {
        boolean validateInput = classroomValidation.validateInput(requestClassroom);
        if (validateInput) {
            ClassroomEntity classroomEntity = getEntityCreate(requestClassroom);
            classroomRepository.save(classroomEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(classroomEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteClassroom(Integer id) {
        if (classroomRepository.existsById(id)) {
            Optional<ClassroomEntity> classroomEntityToDelete = classroomRepository.findById(id);
            if (classroomEntityToDelete.isPresent()) {
                ClassroomEntity classroomEntityDeleted = getEntityDelete(classroomEntityToDelete.get());
                classroomRepository.save(classroomEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(classroomEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneClassroom(Integer id) {
        if (classroomRepository.existsById(id)) {
            Optional<ClassroomEntity> classroomEntity = classroomRepository.findById(id);
            if(classroomEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, classroomEntity);
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllClassrooms() {
        List<ClassroomEntity> classroomEntityList = classroomRepository.findAll();
        if (!classroomEntityList.isEmpty()) {
            classroomEntityList.sort(Comparator.comparingInt(ClassroomEntity::getClassroomId));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(classroomEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private ClassroomEntity getEntityCreate(RequestClassroom requestClassroom) {
        ClassroomEntity classroomEntity = new ClassroomEntity();
        classroomEntity.setCoursesEntity(getCourse(requestClassroom.getCourseEntityId()));
        classroomEntity.setTeachersEntity(getTeacher(requestClassroom.getTeachersEntityId()));
        classroomEntity.setClassTitle(requestClassroom.getClassTitle());
        classroomEntity.setClassDate(new Timestamp(System.currentTimeMillis()));
        classroomEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        classroomEntity.setStatus(Constants.STATUS_ACTIVE);
        classroomEntity.setUserCreate(Constants.AUDIT_ADMIN);
        return classroomEntity;
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

    private ClassroomEntity getEntityDelete(ClassroomEntity classroomEntity) {
        classroomEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        classroomEntity.setStatus(Constants.STATUS_INACTIVE);
        classroomEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return classroomEntity;
    }

    private ClassroomEntity getEntityUpdate(RequestClassroom requestClassroom, ClassroomEntity classroomEntity) {
        classroomEntity.setClassTitle(requestClassroom.getClassTitle());
        classroomEntity.setCoursesEntity(getCourse(requestClassroom.getCourseEntityId()));
        classroomEntity.setTeachersEntity(getTeacher(requestClassroom.getTeachersEntityId()));
        classroomEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        classroomEntity.setUserModify(Constants.AUDIT_ADMIN);
        return classroomEntity;
    }

    private TeachersEntity getTeacher(int teacherEntityId) {
        if (teachersRepository.existsById(teacherEntityId)) {
            Optional<TeachersEntity> teachersEntity = teachersRepository.findById(teacherEntityId);
            return teachersEntity.orElse(null);
        }
        else {
            return null;
        }
    }

    @Override
    public ResponseBase updateClassroom(Integer id, RequestClassroom requestClassroom) {
        boolean validationInput = classroomValidation.validateInput(requestClassroom);
        if (validationInput) {
            if (classroomRepository.existsById(id)) {
                Optional<ClassroomEntity> classroomEntityToUpdate = classroomRepository.findById(id);
                if (classroomEntityToUpdate.isPresent()) {
                    ClassroomEntity classroomEntityUpdated = getEntityUpdate(requestClassroom, classroomEntityToUpdate.get());
                    classroomRepository.save(classroomEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(classroomEntityUpdated));
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
