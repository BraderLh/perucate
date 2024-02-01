package com.balh.perucate.controller;

import com.balh.perucate.agreggates.request.RequestTeacher;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.TeachersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/teacher")
public class TeachersController {
    private final TeachersService teachersService;

    public TeachersController(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    @PostMapping
    public ResponseBase createTeacher(@RequestBody RequestTeacher requestTeacher) {
        return teachersService.createTeacher(requestTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteTeacher(@PathVariable Integer id) {
        return teachersService.deleteTeacher(id);
    }

    @GetMapping
    public ResponseBase findAllTeachers() {
        return teachersService.findAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseBase findOneTeacher(@PathVariable Integer id) {
        return teachersService.findOneTeacher(id);
    }

    @PatchMapping("/{id}")
    public ResponseBase updateTeacher(@PathVariable Integer id, @RequestBody RequestTeacher requestTeacher) {
        return teachersService.updateTeacher(id, requestTeacher);
    }
}
