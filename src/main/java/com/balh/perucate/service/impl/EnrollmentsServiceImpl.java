package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestCancelEnrollment;
import com.balh.perucate.agreggates.request.RequestEnrollment;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.CancelEnrolmentEntity;
import com.balh.perucate.entity.CoursesEntity;
import com.balh.perucate.entity.EnrollmentsEntity;
import com.balh.perucate.entity.StudentsEntity;
import com.balh.perucate.repository.CancelEnrollmentsRepository;
import com.balh.perucate.repository.CoursesRepository;
import com.balh.perucate.repository.EnrollmentsRepository;
import com.balh.perucate.repository.StudentsRepository;
import com.balh.perucate.service.EnrollmentsService;
import com.balh.perucate.util.validations.EnrollmentValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentsServiceImpl implements EnrollmentsService {
    private final CancelEnrollmentsRepository cancelEnrollmentsRepository;
    private final CoursesRepository coursesRepository;
    private final EnrollmentsRepository enrollmentsRepository;
    private final EnrollmentValidation enrollmentValidation;
    private final StudentsRepository studentsRepository;

    public EnrollmentsServiceImpl(CancelEnrollmentsRepository cancelEnrollmentsRepository, CoursesRepository coursesRepository, EnrollmentsRepository enrollmentsRepository, EnrollmentValidation enrollmentValidation, StudentsRepository studentsRepository) {
        this.cancelEnrollmentsRepository = cancelEnrollmentsRepository;
        this.coursesRepository = coursesRepository;
        this.enrollmentsRepository = enrollmentsRepository;
        this.enrollmentValidation = enrollmentValidation;
        this.studentsRepository = studentsRepository;
    }

    @Override
    public ResponseBase createEnrollment(RequestEnrollment requestEnrollment) {
        boolean validateInput = enrollmentValidation.validateInput(requestEnrollment);
        if (validateInput) {
            EnrollmentsEntity enrollmentsEntity = getEntityCreate(requestEnrollment);
            enrollmentsRepository.save(enrollmentsEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(enrollmentsEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteEnrollment(Integer id) {
        if (enrollmentsRepository.existsById(id)) {
            Optional<EnrollmentsEntity> enrollmentsEntity = enrollmentsRepository.findById(id);
            if (enrollmentsEntity.isPresent()) {
                EnrollmentsEntity enrollmentEntityDeleted = getEntityDelete(enrollmentsEntity.get());
                enrollmentsRepository.save(enrollmentEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(enrollmentEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneEnrollment(Integer id) {
        if (enrollmentsRepository.existsById(id)) {
            Optional<EnrollmentsEntity> enrollmentEntityToFind = enrollmentsRepository.findById(id);
            if(enrollmentEntityToFind.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, enrollmentEntityToFind);
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllEnrollments() {
        List<EnrollmentsEntity> enrollmentsEntities = enrollmentsRepository.findAll();
        if (!enrollmentsEntities.isEmpty()) {
            enrollmentsEntities.sort(Comparator.comparingInt(EnrollmentsEntity::getEnrollmentId));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(enrollmentsEntities));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private EnrollmentsEntity getEntityCreate(RequestEnrollment requestEnrollment) {
        EnrollmentsEntity enrollmentsEntity = new EnrollmentsEntity();
        enrollmentsEntity.setCoursesEntityEnrolled(getCourse(requestEnrollment.getCoursesEntityEnrolledId()));
        enrollmentsEntity.setStudentsEntityEnrolled(getStudent(requestEnrollment.getStudentsEntityEnrolledId()));
        enrollmentsEntity.setEnrollmentDate(new Timestamp(System.currentTimeMillis()));
        enrollmentsEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        enrollmentsEntity.setStatus(Constants.STATUS_ACTIVE);
        enrollmentsEntity.setUserCreate(Constants.AUDIT_ADMIN);
        return enrollmentsEntity;
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

    private StudentsEntity getStudent(int studentsEntityEnrolledId) {
        if (studentsRepository.existsById(studentsEntityEnrolledId)) {
            Optional<StudentsEntity> studentsEntity = studentsRepository.findById(studentsEntityEnrolledId);
            return studentsEntity.orElse(null);
        }
        else {
            return null;
        }
    }

    private EnrollmentsEntity getEntityDelete(EnrollmentsEntity enrollmentsEntity) {
        enrollmentsEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        enrollmentsEntity.setStatus(Constants.STATUS_INACTIVE);
        enrollmentsEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return enrollmentsEntity;
    }

    private EnrollmentsEntity getEntityUpdate(RequestEnrollment requestEnrollment, EnrollmentsEntity enrollmentsEntity) {
        enrollmentsEntity.setCoursesEntityEnrolled(getCourse(requestEnrollment.getCoursesEntityEnrolledId()));
        enrollmentsEntity.setStudentsEntityEnrolled(getStudent(requestEnrollment.getStudentsEntityEnrolledId()));
        enrollmentsEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        enrollmentsEntity.setUserModify(Constants.AUDIT_ADMIN);
        return enrollmentsEntity;
    }

    private CancelEnrolmentEntity getSuspendEnrollment(RequestCancelEnrollment requestCancelEnrollment) {
        CancelEnrolmentEntity cancelEnrolmentEntity = new CancelEnrolmentEntity();
        cancelEnrolmentEntity.setCancellationReason(requestCancelEnrollment.getCancellationReason());
        cancelEnrolmentEntity.setCancelDate(new Timestamp(System.currentTimeMillis()));
        cancelEnrolmentEntity.setUserCancel(Constants.AUDIT_USER);
        cancelEnrolmentEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        cancelEnrolmentEntity.setUserCreate(Constants.AUDIT_ADMIN);
        cancelEnrolmentEntity.setStatus(Constants.STATUS_ACTIVE);
        return cancelEnrolmentEntity;
    }

    @Override
    public ResponseBase cancelEnrollment(RequestCancelEnrollment requestCancelEnrollment) {
        boolean validationInput = enrollmentValidation.validateCancelInput(requestCancelEnrollment);
        if (validationInput) {
            if (enrollmentsRepository.existsById(requestCancelEnrollment.getEnrollmentsEntityId())) {
                Optional<EnrollmentsEntity> enrollmentEntityToSuspend = enrollmentsRepository.findById(requestCancelEnrollment.getEnrollmentsEntityId());
                CancelEnrolmentEntity cancelEnrolmentEntity = getSuspendEnrollment(requestCancelEnrollment);
                if (enrollmentEntityToSuspend.isPresent()) {
                    enrollmentEntityToSuspend.get().setStatus(Constants.STATUS_INACTIVE);
                    enrollmentEntityToSuspend.get().setUserDelete(Constants.AUDIT_USER);
                    cancelEnrollmentsRepository.save(cancelEnrolmentEntity);
                    enrollmentsRepository.save(enrollmentEntityToSuspend.get());
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(enrollmentEntityToSuspend));
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

    @Override
    public ResponseBase updateEnrollment(Integer id, RequestEnrollment requestEnrollment) {
        boolean validationInput = enrollmentValidation.validateInput(requestEnrollment);
        if (validationInput) {
            if (enrollmentsRepository.existsById(id)) {
                Optional<EnrollmentsEntity> enrollmentEntityToUpdate = enrollmentsRepository.findById(id);
                if (enrollmentEntityToUpdate.isPresent()) {
                    EnrollmentsEntity enrollmentEntityUpdated = getEntityUpdate(requestEnrollment, enrollmentEntityToUpdate.get());
                    enrollmentsRepository.save(enrollmentEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(enrollmentEntityUpdated));
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
