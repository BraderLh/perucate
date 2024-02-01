package com.balh.perucate.controller;

import com.balh.perucate.agreggates.request.RequestStudent;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.StudentsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/student")
public class StudentsController {
    private final StudentsService studentsService;

    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @PostMapping
    public ResponseBase createStudent(@RequestBody RequestStudent requestStudent) {
        return studentsService.createStudent(requestStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteStudent(@PathVariable int id) {
        return studentsService.deleteStudent(id);
    }

    @GetMapping
    public ResponseBase findAllStudents() {
        return studentsService.findAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseBase findOneStudent(@PathVariable int id) {
        return studentsService.findOneStudent(id);
    }

    @PatchMapping("/{id}")
    public ResponseBase updateStudent(@PathVariable int id, @RequestBody RequestStudent requestStudent) {
        return studentsService.updateStudent(id, requestStudent);
    }
}
