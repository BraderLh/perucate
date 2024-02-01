package com.balh.perucate.controller;

import com.balh.perucate.agreggates.request.RequestCourse;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.CoursesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/course")
public class CourseController {
    private final CoursesService coursesService;

    public CourseController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @PostMapping
    public ResponseBase createCourse(@RequestBody RequestCourse requestCourse) {
        return coursesService.createCourse(requestCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteCourse(@PathVariable Integer id) {
        return coursesService.deleteCourse(id);
    }

    @GetMapping
    public ResponseBase findAllCourses() {
        return coursesService.findAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseBase findOneCourse(@PathVariable Integer id) {
        return coursesService.findOneCourse(id);
    }

    @PatchMapping("/{id}")
    public ResponseBase updateCourse(@PathVariable Integer id, @RequestBody RequestCourse requestCourse) {
        return coursesService.updateCourse(id, requestCourse);
    }
}
