package com.balh.perucate.service.impl;

import com.balh.libreriacodigo.config.RedisService;
import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestStudent;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.agreggates.response.ResponseReniec;
import com.balh.perucate.entity.DocumentsTypeEntity;
import com.balh.perucate.entity.StudentsEntity;
import com.balh.perucate.feignclient.ReniecClient;
import com.balh.perucate.repository.DocumentsTypeEntityRepository;
import com.balh.perucate.repository.StudentsRepository;
import com.balh.perucate.service.StudentsService;
import com.balh.perucate.util.Util;
import com.balh.perucate.util.validations.StudentValidation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsServiceImpl implements StudentsService {
    private final DocumentsTypeEntityRepository documentsTypeEntityRepository;
    private final ReniecClient reniecClient;
    private final RedisService redisService;
    private final StudentsRepository studentsRepository;
    private final StudentValidation studentValidation;

    @Value("${token.api.reniec}")
    private String tokenReniec;

    @Value("${time.expiration.reniec.info}")
    private String timeExpirationReniecInfo;

    public StudentsServiceImpl(DocumentsTypeEntityRepository documentsTypeEntityRepository, ReniecClient reniecClient, RedisService redisService, StudentsRepository studentsRepository, StudentValidation studentValidation) {
        this.documentsTypeEntityRepository = documentsTypeEntityRepository;
        this.reniecClient = reniecClient;
        this.redisService = redisService;
        this.studentsRepository = studentsRepository;
        this.studentValidation = studentValidation;
    }

    @Override
    public ResponseBase createStudent(RequestStudent requestStudent) {
        boolean inputValidation = studentValidation.validateInput(requestStudent);
        if (inputValidation) {
            StudentsEntity studentsEntityToCreate = getEntityCreate(requestStudent);
            studentsRepository.save(studentsEntityToCreate);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(studentsEntityToCreate));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteStudent(Integer id) {
        if (studentsRepository.existsById(id)) {
            Optional<StudentsEntity> studentEntityToDelete = studentsRepository.findById(id);
            if (studentEntityToDelete.isPresent()) {
                StudentsEntity studentsEntityDeleted = getEntityDelete(studentEntityToDelete.get());
                studentsRepository.save(studentsEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(studentsEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneStudent(String doc) {
        String redisCache = redisService.getValueFromCache(Constants.REDIS_KEY_INFO_RENIEC_REDIS + doc);
        if (redisCache!=null) {
            StudentsEntity studentsEntity = Util.convertFromJson(redisCache, StudentsEntity.class);
            if (studentsEntity != null) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(studentsEntity));
            }
            else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }

        } else {
            StudentsEntity studentEntityToFind = studentsRepository.findByNumDoc(doc);
            if(studentEntityToFind != null) {
                String redisData = Util.convertToJsonEntity(studentEntityToFind);
                redisService.saveInCache(Constants.REDIS_KEY_INFO_RENIEC_REDIS + doc, redisData, Integer.parseInt(timeExpirationReniecInfo));
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(studentEntityToFind));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        }
    }

    @Override
    public ResponseBase findAllStudents() {
        List<StudentsEntity> studentsEntityList = studentsRepository.findAll();
        if (!studentsEntityList.isEmpty()) {
            studentsEntityList.sort(Comparator.comparingInt(StudentsEntity::getIdStudent));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(studentsEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    private StudentsEntity getEntityCreate(RequestStudent requestStudent) {
        StudentsEntity studentsEntity = new StudentsEntity();
        ResponseReniec responseReniec = getExecutionReniec(requestStudent.getNumDocument());
        if (responseReniec != null) {
            studentsEntity.setNumDocument(responseReniec.getNumeroDocumento());
            studentsEntity.setName(responseReniec.getNombres());
            studentsEntity.setSurname(responseReniec.getApellidoPaterno() + " " + responseReniec.getApellidoMaterno());
        } else {
            studentsEntity.setNumDocument(requestStudent.getNumDocument());
            studentsEntity.setName(requestStudent.getName());
            studentsEntity.setSurname(requestStudent.getSurname());
        }
        studentsEntity.setTelephone(requestStudent.getTelephone());
        studentsEntity.setAge(requestStudent.getAge());
        studentsEntity.setDocumentsTypeEntity(getDocumentsType(requestStudent.getDocumentTypeEntityId()));
        studentsEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        studentsEntity.setStatus(Constants.STATUS_ACTIVE);
        studentsEntity.setUserCreate(Constants.AUDIT_ADMIN);
        return studentsEntity;
    }

    private DocumentsTypeEntity getDocumentsType(int documentTypeEntityId) {
        if (documentsTypeEntityRepository.existsById(documentTypeEntityId)) {
            Optional<DocumentsTypeEntity> documentsTypeEntity = documentsTypeEntityRepository.findById(documentTypeEntityId);
            return documentsTypeEntity.orElse(null);
        } else {
            return null;
        }
    }

    private StudentsEntity getEntityDelete(StudentsEntity studentsEntity) {
        studentsEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        studentsEntity.setStatus(Constants.STATUS_INACTIVE);
        studentsEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return studentsEntity;
    }

    private StudentsEntity getEntityUpdate(RequestStudent requestStudent, StudentsEntity studentsEntity) {
        studentsEntity.setNumDocument(requestStudent.getNumDocument());
        studentsEntity.setName(requestStudent.getName());
        studentsEntity.setSurname(requestStudent.getSurname());
        studentsEntity.setTelephone(requestStudent.getTelephone());
        studentsEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        studentsEntity.setUserModify(Constants.AUDIT_ADMIN);
        return studentsEntity;
    }

    private ResponseReniec getExecutionReniec(String numero) {
        String authorization = "Bearer " + tokenReniec;
        ResponseReniec reniec = reniecClient.getInfoReniec(numero, authorization);
        if (reniec.getTipoDocumento() != null) {
            String redisData = Util.convertToJson(reniec);
            redisService.saveInCache(Constants.REDIS_KEY_API_RENIEC_INFO + numero, redisData, Integer.parseInt(timeExpirationReniecInfo));
            //return reniecClient.getInfoReniec(numero, authorization);
            return reniec;
        } else {
            return null;
        }
    }

    @Override
    public ResponseBase updateStudent(Integer id, RequestStudent requestStudent) {
        boolean validationInput = studentValidation.validateInput(requestStudent);
        if (validationInput) {
            if (studentsRepository.existsById(id)) {
                Optional<StudentsEntity> studentEntityToUpdate = studentsRepository.findById(id);
                if (studentEntityToUpdate.isPresent()) {
                    StudentsEntity studentsEntityUpdated = getEntityUpdate(requestStudent, studentEntityToUpdate.get());
                    studentsRepository.save(studentsEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(studentsEntityUpdated));
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
