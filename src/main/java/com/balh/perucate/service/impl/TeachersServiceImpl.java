package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestTeacher;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.CoursesEntity;
import com.balh.perucate.entity.DocumentsTypeEntity;
import com.balh.perucate.entity.TeachersEntity;
import com.balh.perucate.repository.CoursesRepository;
import com.balh.perucate.repository.DocumentsTypeEntityRepository;
import com.balh.perucate.repository.TeachersRepository;
import com.balh.perucate.service.TeachersService;
import com.balh.perucate.util.validations.TeacherValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class TeachersServiceImpl implements TeachersService {
    private final CoursesRepository coursesRepository;
    private final DocumentsTypeEntityRepository documentsTypeEntityRepository;
    private final TeachersRepository teachersRepository;
    private final TeacherValidation teacherValidation;

    public TeachersServiceImpl(CoursesRepository coursesRepository, DocumentsTypeEntityRepository documentsTypeEntityRepository, TeachersRepository teachersRepository, TeacherValidation teacherValidation) {
        this.coursesRepository = coursesRepository;
        this.documentsTypeEntityRepository = documentsTypeEntityRepository;
        this.teachersRepository = teachersRepository;
        this.teacherValidation = teacherValidation;
    }

    @Override
    public ResponseBase createTeacher(RequestTeacher requestTeacher) {
        boolean inputValidation = teacherValidation.validateInput(requestTeacher);
        if (inputValidation) {
            TeachersEntity teachersEntityToCreate = getEntityCreate(requestTeacher);
            teachersRepository.save(teachersEntityToCreate);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(teachersEntityToCreate));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteTeacher(Integer id) {
        if (teachersRepository.existsById(id)) {
            Optional<TeachersEntity> teacherEntityToDelete = teachersRepository.findById(id);
            if (teacherEntityToDelete.isPresent()) {
                TeachersEntity teachersEntityDeleted = getEntityDelete(teacherEntityToDelete.get());
                teachersRepository.save(teachersEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(teachersEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneTeacher(Integer id) {
        if (teachersRepository.existsById(id)) {
            Optional<TeachersEntity> teacherEntityToFind = teachersRepository.findById(id);
            if(teacherEntityToFind.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, teacherEntityToFind);
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllTeachers() {
        List<TeachersEntity> teachersEntityList = teachersRepository.findAll();
        if (!teachersEntityList.isEmpty()) {
            teachersEntityList.sort(Comparator.comparingInt(TeachersEntity::getIdTeacher));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(teachersEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private TeachersEntity getEntityCreate(RequestTeacher requestTeacher) {
        TeachersEntity teachersEntity = new TeachersEntity();
        teachersEntity.setNumDocument(requestTeacher.getNumDocument());
        teachersEntity.setName(requestTeacher.getName());
        teachersEntity.setSurname(requestTeacher.getSurname());
        teachersEntity.setAddress(requestTeacher.getAddress());
        teachersEntity.setAge(requestTeacher.getAge());
        teachersEntity.setTelephone(requestTeacher.getTelephone());
        teachersEntity.setCareer(requestTeacher.getCareer());
        teachersEntity.setDegree(requestTeacher.getDegree());
        teachersEntity.setSpecialization(requestTeacher.getSpecialization());
        teachersEntity.setUniversityName(requestTeacher.getUniversityName());
        teachersEntity.setDocumentsTypeEntity(getDocumentsType(requestTeacher.getDocumentTypeEntityId()));
        teachersEntity.setCourseEntitiesSet(getCourses(requestTeacher.getIdsCourseEntities()));
        teachersEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        teachersEntity.setStatus(Constants.STATUS_ACTIVE);
        teachersEntity.setUserCreate(Constants.AUDIT_ADMIN);
        return teachersEntity;
    }

    private Set<CoursesEntity> getCourses(Set<CoursesEntity> idsCourseEntities) {
        Set<CoursesEntity> courseEntities = new HashSet<>();
        for (CoursesEntity idsCoursesEntity : idsCourseEntities) {
            if (coursesRepository.existsById(idsCoursesEntity.getIdCourse())) {
                courseEntities.add(idsCoursesEntity);
            }
        }
        return courseEntities;
    }

    private DocumentsTypeEntity getDocumentsType(int documentTypeEntityId) {
        if (documentsTypeEntityRepository.existsById(documentTypeEntityId)) {
            Optional<DocumentsTypeEntity> documentsTypeEntity = documentsTypeEntityRepository.findById(documentTypeEntityId);
            return documentsTypeEntity.orElse(null);
        } else {
            return null;
        }
    }

    private TeachersEntity getEntityDelete(TeachersEntity teachersEntity) {
        teachersEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        teachersEntity.setStatus(Constants.STATUS_INACTIVE);
        teachersEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return teachersEntity;
    }

    private TeachersEntity getEntityUpdate(RequestTeacher requestTeacher, TeachersEntity teachersEntity) {
        teachersEntity.setNumDocument(requestTeacher.getNumDocument());
        teachersEntity.setName(requestTeacher.getName());
        teachersEntity.setSurname(requestTeacher.getSurname());
        teachersEntity.setAddress(requestTeacher.getAddress());
        teachersEntity.setTelephone(requestTeacher.getTelephone());
        teachersEntity.setAge(requestTeacher.getAge());
        teachersEntity.setCareer(requestTeacher.getCareer());
        teachersEntity.setDegree(requestTeacher.getDegree());
        teachersEntity.setSpecialization(requestTeacher.getSpecialization());
        teachersEntity.setUniversityName(requestTeacher.getUniversityName());
        teachersEntity.setCourseEntitiesSet(getCourses(requestTeacher.getIdsCourseEntities()));
        teachersEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        teachersEntity.setUserModify(Constants.AUDIT_ADMIN);
        return teachersEntity;
    }

    @Override
    public ResponseBase updateTeacher(Integer id, RequestTeacher requestTeacher) {
        boolean validationInput = teacherValidation.validateInput(requestTeacher);
        if (validationInput) {
            if (teachersRepository.existsById(id)) {
                Optional<TeachersEntity> teacherEntityToUpdate = teachersRepository.findById(id);
                if (teacherEntityToUpdate.isPresent()) {
                    TeachersEntity teachersEntityUpdated = getEntityUpdate(requestTeacher, teacherEntityToUpdate.get());
                    teachersRepository.save(teachersEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(teachersEntityUpdated));
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
