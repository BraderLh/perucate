package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.request.RequestCourse;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.CoursesEntity;
import com.balh.perucate.entity.EnrollmentsEntity;
import com.balh.perucate.repository.CoursesRepository;
import com.balh.perucate.util.validations.CourseValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CoursesServiceImplTest {
    @Mock
    CoursesRepository coursesRepository;
    @Mock
    CourseValidation courseValidation;

    @InjectMocks
    CoursesServiceImpl coursesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coursesService = new CoursesServiceImpl(coursesRepository, courseValidation);
    }

    @Test
    void createCourse() {
        boolean validateCourse = true;
        Set<EnrollmentsEntity> enrollmentsEntities = new HashSet<>();
        RequestCourse requestCourse = RequestCourse.builder().code("PCDW001")
                .name("Curso de Desarrollo Web con Java").bibliography("Biblio1, Bilbio2")
                .description("Lleva este curso completamente por tan sólo 100 so").build();
        CoursesEntity coursesEntity = new CoursesEntity(1, "PCDW001", "Curso de Desarrollo Web con Java",
                "Biblio1, Bilbio2", "Lleva este curso completamente por tan sólo 100 so",
                enrollmentsEntities);
        coursesEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        coursesEntity.setStatus(Constants.STATUS_ACTIVE);
        coursesEntity.setUserCreate(Constants.AUDIT_ADMIN);

        ResponseBase responseBaseExpected = new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(coursesEntity));

        Mockito.when(courseValidation.validateInput(Mockito.any(RequestCourse.class))).thenReturn(validateCourse);
        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntity);

        ResponseBase responseBaseObtained = coursesService.createCourse(requestCourse);

        assertNotNull(responseBaseObtained);
        assertEquals(responseBaseExpected.getCode(), responseBaseObtained.getCode());
        assertEquals(responseBaseExpected.getMessage(), responseBaseObtained.getMessage());

        if (responseBaseExpected.getData().isPresent() && responseBaseObtained.getData().isPresent()){
            CoursesEntity coursesEntityExpected = (CoursesEntity) responseBaseExpected.getData().get();
            CoursesEntity coursesEntityObtained = (CoursesEntity) responseBaseObtained.getData().get();
            assertEquals(coursesEntityExpected.getCode(), coursesEntityObtained.getCode());
            assertEquals(coursesEntityExpected.getName(), coursesEntityObtained.getName());
            assertEquals(coursesEntityExpected.getBibliography(), coursesEntityObtained.getBibliography());
            assertEquals(coursesEntityExpected.getDescription(), coursesEntityObtained.getDescription());
        }
    }

    @Test
    void deleteCourse() {
        boolean existsId = true;
        Set<EnrollmentsEntity> enrollmentsEntities = new HashSet<>();
        CoursesEntity coursesEntityDeleted = new CoursesEntity(2, "PCDW002",
                "Curso de Desarrollo Web con PHP", "Biblio1, Bilbio2",
                "Lleva este curso completamente por tan sólo 80 so",
                enrollmentsEntities);
        coursesEntityDeleted.setDateDelete(new Timestamp(System.currentTimeMillis()));
        coursesEntityDeleted.setStatus(Constants.STATUS_INACTIVE);
        coursesEntityDeleted.setUserDelete(Constants.AUDIT_ADMIN);

        Mockito.when(coursesRepository.existsById(Mockito.anyInt())).thenReturn(existsId);
        Mockito.when(coursesRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(coursesEntityDeleted));
        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntityDeleted);

        ResponseBase responseBaseExpected = new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS,
                Optional.of(coursesEntityDeleted));

        ResponseBase responseBaseObtained = coursesService.deleteCourse(2);

        assertNotNull(responseBaseObtained);
        assertEquals(responseBaseExpected.getCode(), responseBaseObtained.getCode());
        assertEquals(responseBaseExpected.getMessage(), responseBaseObtained.getMessage());
        assertEquals(responseBaseExpected.getData(), responseBaseObtained.getData());

        if (responseBaseExpected.getData().isPresent() && responseBaseObtained.getData().isPresent()) {
            CoursesEntity coursesEntity1 = (CoursesEntity) responseBaseExpected.getData().get();
            CoursesEntity coursesEntity2 = (CoursesEntity) responseBaseObtained.getData().get();
            assertEquals(coursesEntity1.getStatus(), coursesEntity2.getStatus());
            assertEquals(coursesEntity1.getDateDelete(), coursesEntity2.getDateDelete());
            assertEquals(coursesEntity1.getUserDelete(), coursesEntity2.getUserDelete());
        }
    }

    @Test
    void findOneCourse() {
        boolean existsId = true;
        Set<EnrollmentsEntity> enrollmentsEntities = new HashSet<>();
        CoursesEntity coursesEntityFound = new CoursesEntity(3, "PCDW003",
                "Curso de Desarrollo Web con Python", "Bibliografía 1, Bibliografía 2, Bibliografía 3",
                "Lleva este curso completamente por tan sólo 90 so",
                enrollmentsEntities);
        coursesEntityFound.setDateModify(new Timestamp(System.currentTimeMillis()));
        coursesEntityFound.setStatus(Constants.STATUS_ACTIVE);
        coursesEntityFound.setUserModify(Constants.AUDIT_ADMIN);

        ResponseBase responseBaseExpected = new ResponseBase(Constants.CODE_SUCCESS,
                Constants.MESS_SUCCESS, Optional.of(coursesEntityFound));

        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntityFound);
        Mockito.when(coursesRepository.existsById(Mockito.anyInt())).thenReturn(existsId);
        Mockito.when(coursesRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(coursesEntityFound));

        ResponseBase responseBaseObtained = coursesService.findOneCourse(3);

        assertNotNull(responseBaseObtained);
        assertEquals(responseBaseExpected.getCode(), responseBaseObtained.getCode());
        assertEquals(responseBaseExpected.getMessage(), responseBaseObtained.getMessage());
        assertEquals(responseBaseExpected.getData(), responseBaseObtained.getData());

        if (responseBaseExpected.getData().isPresent() && responseBaseObtained.getData().isPresent()) {
            CoursesEntity coursesEntity1 = (CoursesEntity) responseBaseExpected.getData().get();
            CoursesEntity coursesEntity2 = (CoursesEntity) responseBaseObtained.getData().get();
            assertEquals(coursesEntity1.getName(), coursesEntity2.getName());
            assertEquals(coursesEntity1.getCode(), coursesEntity2.getCode());
            assertEquals(coursesEntity1.getBibliography(), coursesEntity2.getBibliography());
            assertEquals(coursesEntity1.getIdCourse(), coursesEntity2.getIdCourse());
        }
    }

    @Test
    void findAllCourses() {
        Set<EnrollmentsEntity> enrollmentsEntities = new HashSet<>();
        List<CoursesEntity> coursesEntityList = new ArrayList<>();
        CoursesEntity coursesEntity1 = new CoursesEntity(1, "PCDW001", "Curso de Desarrollo Web con Java",
                "Bibliografia 1, Bibliografia 2", "Lleva este curso completamente por tan sólo 100 so",
                enrollmentsEntities);
        CoursesEntity coursesEntity2 = new CoursesEntity(2, "PCDW002", "Curso de Desarrollo Web con PHP",
                "Bibliografia 1, Bibliografia 2, Bibliografia 3", "Lleva este curso completamente por tan sólo 100 so",
                enrollmentsEntities);
        CoursesEntity coursesEntity3 = new CoursesEntity(3, "PCDW003", "Curso de Desarrollo Web con Go",
                "Bibliografia 1", "Lleva este curso completamente por tan sólo 100 so",
                enrollmentsEntities);

        coursesEntityList.add(coursesEntity1);
        coursesEntityList.add(coursesEntity2);
        coursesEntityList.add(coursesEntity3);

        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntity1);
        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntity2);
        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntity3);
        Mockito.when(coursesRepository.findAll()).thenReturn(coursesEntityList);

        coursesRepository.save(coursesEntity1);
        coursesRepository.save(coursesEntity2);
        coursesRepository.save(coursesEntity3);

        ResponseBase responseBaseExpected = new ResponseBase(Constants.CODE_SUCCESS,
                Constants.MESS_SUCCESS, Optional.of(coursesEntityList));

        ResponseBase responseBaseObtained = coursesService.findAllCourses();

        assertNotNull(responseBaseObtained);
        assertEquals(responseBaseExpected.getCode(), responseBaseObtained.getCode());
        assertEquals(responseBaseExpected.getMessage(), responseBaseObtained.getMessage());
        assertEquals(responseBaseExpected.getData(), responseBaseObtained.getData());

        if (responseBaseExpected.getData().isPresent() && responseBaseObtained.getData().isPresent()) {
            Object object1 = responseBaseExpected.getData().get();
            Object object2 = responseBaseObtained.getData().get();
            List<CoursesEntity> coursesEntityList1 = (List<CoursesEntity>) object1;
            List<CoursesEntity> coursesEntityList2 = (List<CoursesEntity>) object2;
            assertFalse(coursesEntityList2.isEmpty());
            assertEquals(coursesEntityList1.size(), coursesEntityList2.size());
        }
    }

    @Test
    void updateCourse() {
        boolean existsId = true;
        boolean validateCourse = true;

        Set<EnrollmentsEntity> enrollmentsEntities = new HashSet<>();
        CoursesEntity coursesEntityToUpdate = new CoursesEntity(2, "PCDW02",
                "Curso de Desarrollo Web con Laravel", "Biblio1, Bilbio2",
                "Lleva este curso completamente por tan sólo 89 so",
                enrollmentsEntities);
        coursesEntityToUpdate.setDateDelete(new Timestamp(System.currentTimeMillis()));
        coursesEntityToUpdate.setStatus(Constants.STATUS_ACTIVE);
        coursesEntityToUpdate.setUserDelete(Constants.AUDIT_ADMIN);

        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntityToUpdate);
        coursesRepository.save(coursesEntityToUpdate);

        RequestCourse requestCourse = RequestCourse.builder().code("PCDW002")
                .name("Curso de Desarrollo Web con PHP").bibliography("Bibliografía 1, Bibliografía 2, Bibliografía 3")
                .description("Lleva este curso completamente por tan sólo 100 so").build();

        Mockito.when(courseValidation.validateInput(Mockito.any(RequestCourse.class))).thenReturn(validateCourse);
        Mockito.when(coursesRepository.existsById(Mockito.anyInt())).thenReturn(existsId);
        Mockito.when(coursesRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(coursesEntityToUpdate));

        CoursesEntity coursesEntityUpdated = new CoursesEntity(2, "PCDW002",
                "Curso de Desarrollo Web con PHP", "Bibliografía 1, Bibliografía 2, Bibliografía 3",
                "Lleva este curso completamente por tan sólo 100 so",
                enrollmentsEntities);
        coursesEntityUpdated.setDateModify(new Timestamp(System.currentTimeMillis()));
        coursesEntityUpdated.setStatus(Constants.STATUS_ACTIVE);
        coursesEntityUpdated.setUserModify(Constants.AUDIT_ADMIN);

        Mockito.when(coursesRepository.save(Mockito.any(CoursesEntity.class))).thenReturn(coursesEntityUpdated);
        coursesRepository.save(coursesEntityUpdated);

        ResponseBase responseBaseExpected = new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS,
                Optional.of(coursesEntityUpdated));

        ResponseBase responseBaseObtained = coursesService.updateCourse(2, requestCourse);

        assertNotNull(responseBaseObtained);
        assertEquals(responseBaseExpected.getCode(), responseBaseObtained.getCode());
        assertEquals(responseBaseExpected.getMessage(), responseBaseObtained.getMessage());

        if (responseBaseExpected.getData().isPresent() && responseBaseObtained.getData().isPresent()) {
            CoursesEntity coursesEntity1 = (CoursesEntity) responseBaseExpected.getData().get();
            CoursesEntity coursesEntity2 = (CoursesEntity) responseBaseObtained.getData().get();
            assertEquals(coursesEntity1.getName(), coursesEntity2.getName());
            assertEquals(coursesEntity1.getCode(), coursesEntity2.getCode());
            assertEquals(coursesEntity1.getBibliography(), coursesEntity2.getBibliography());
            assertEquals(coursesEntity1.getIdCourse(), coursesEntity2.getIdCourse());
            assertEquals(coursesEntity1.getUserModify(), coursesEntity2.getUserModify());
        }
    }
}