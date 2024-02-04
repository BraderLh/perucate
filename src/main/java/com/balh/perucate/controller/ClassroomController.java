package com.balh.perucate.controller;

import com.balh.perucate.agreggates.request.RequestClassroom;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.ClassroomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/classroom")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping
    public ResponseBase createScore(@RequestBody RequestClassroom requestClassroom) {
        return classroomService.createClassroom(requestClassroom);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteScore(@PathVariable Integer id) {
        return classroomService.deleteClassroom(id);
    }

    @GetMapping
    public ResponseBase findAllScores() {
        return classroomService.findAllClassrooms();
    }

    @GetMapping("/{id}")
    public ResponseBase findOneScore(@PathVariable Integer id) {
        return classroomService.findOneClassroom(id);
    }

    @PatchMapping("/{id}")
    public ResponseBase updateStudent(@PathVariable Integer id, @RequestBody RequestClassroom requestClassroom) {
        return classroomService.updateClassroom(id, requestClassroom);
    }
}
