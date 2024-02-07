package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestScore;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.ExamEntity;
import com.balh.perucate.entity.ScoreEntity;
import com.balh.perucate.entity.StudentsEntity;
import com.balh.perucate.entity.TeachersEntity;
import com.balh.perucate.repository.ExamRepository;
import com.balh.perucate.repository.ScoreRepository;
import com.balh.perucate.repository.StudentsRepository;
import com.balh.perucate.repository.TeachersRepository;
import com.balh.perucate.service.ScoreService;
import com.balh.perucate.util.validations.ScoreValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService {
    private final ExamRepository examRepository;
    private final ScoreRepository scoreRepository;
    private final ScoreValidation scoreValidation;
    private final StudentsRepository studentsRepository;
    private final TeachersRepository teachersRepository;

    public ScoreServiceImpl(ExamRepository examRepository, ScoreRepository scoreRepository, ScoreValidation scoreValidation, StudentsRepository studentsRepository, TeachersRepository teachersRepository) {
        this.examRepository = examRepository;
        this.scoreRepository = scoreRepository;
        this.scoreValidation = scoreValidation;
        this.studentsRepository = studentsRepository;
        this.teachersRepository = teachersRepository;
    }

    @Override
    public ResponseBase createScore(RequestScore requestScore) {
        boolean inputValidation = scoreValidation.validateInput(requestScore);
        if (inputValidation) {
            ScoreEntity scoreEntityToCreate = getEntityCreate(requestScore);
            scoreRepository.save(scoreEntityToCreate);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(scoreEntityToCreate));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteScore(Integer id) {
        if (scoreRepository.existsById(id)) {
            Optional<ScoreEntity> scoreEntityToDelete = scoreRepository.findById(id);
            if (scoreEntityToDelete.isPresent()) {
                ScoreEntity scoreEntityDeleted = getEntityDelete(scoreEntityToDelete.get());
                scoreRepository.save(scoreEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(scoreEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneScore(Integer id) {
        if (scoreRepository.existsById(id)) {
            Optional<ScoreEntity> scoreEntityToFind = scoreRepository.findById(id);
            if(scoreEntityToFind.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, scoreEntityToFind);
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllScores() {
        List<ScoreEntity> scoreEntityList = scoreRepository.findAll();
        if (!scoreEntityList.isEmpty()) {
            scoreEntityList.sort(Comparator.comparingInt(ScoreEntity::getIdScore));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(scoreEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private ScoreEntity getEntityCreate(RequestScore requestScore) {
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setScore(requestScore.getScore());
        scoreEntity.setExamEntity(getExamEntity(requestScore.getExamEntityId()));
        scoreEntity.setStudentsEntity(getStudentEntity(requestScore.getStudentsEntityId()));
        scoreEntity.setTeachersEntity(getTeacherEntity(requestScore.getTeachersEntityId()));
        return scoreEntity;
    }

    private ExamEntity getExamEntity(int examEntityId) {
        if (examRepository.existsById(examEntityId)) {
            Optional<ExamEntity> examEntity = examRepository.findById(examEntityId);
            return examEntity.orElse(null);
        }
        else {
            return null;
        }
    }

    private StudentsEntity getStudentEntity(int studentsEntityId) {
        if (examRepository.existsById(studentsEntityId)) {
            Optional<StudentsEntity> studentsEntity = studentsRepository.findById(studentsEntityId);
            return studentsEntity.orElse(null);
        }
        else {
            return null;
        }
    }

    private TeachersEntity getTeacherEntity(int teachersEntityId) {
        if (teachersRepository.existsById(teachersEntityId)) {
            Optional<TeachersEntity> teacherEntity = teachersRepository.findById(teachersEntityId);
            return teacherEntity.orElse(null);
        }
        else {
            return null;
        }
    }

    private ScoreEntity getEntityDelete(ScoreEntity scoreEntity) {
        scoreEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        scoreEntity.setStatus(Constants.STATUS_INACTIVE);
        scoreEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return scoreEntity;
    }

    private ScoreEntity getEntityUpdate(RequestScore requestScore, ScoreEntity scoreEntity) {
        scoreEntity.setScore(requestScore.getScore());
        scoreEntity.setExamEntity(getExamEntity(requestScore.getStudentsEntityId()));
        scoreEntity.setTeachersEntity(getTeacherEntity(requestScore.getTeachersEntityId()));
        scoreEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        scoreEntity.setUserModify(Constants.AUDIT_ADMIN);
        return scoreEntity;
    }

    @Override
    public ResponseBase updateScore(Integer id, RequestScore requestScore) {
        boolean validationInput = scoreValidation.validateInput(requestScore);
        if (validationInput) {
            if (scoreRepository.existsById(id)) {
                Optional<ScoreEntity> scoreEntityToUpdate = scoreRepository.findById(id);
                if (scoreEntityToUpdate.isPresent()) {
                    ScoreEntity scoreEntityUpdated = getEntityUpdate(requestScore, scoreEntityToUpdate.get());
                    scoreRepository.save(scoreEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(scoreEntityUpdated));
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
