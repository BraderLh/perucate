package com.balh.perucate.controller;

import com.balh.perucate.agreggates.request.RequestCancelEnrollment;
import com.balh.perucate.agreggates.request.RequestEnrollment;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.EnrollmentsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/enrollments")
public class EnrollmentsController {
    private final EnrollmentsService enrollmentsService;

    public EnrollmentsController(EnrollmentsService enrollmentsService) {
        this.enrollmentsService = enrollmentsService;
    }

    @PostMapping
    public ResponseBase createEnrollment(@RequestBody RequestEnrollment requestEnrollment) {
        return enrollmentsService.createEnrollment(requestEnrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteEnrollment(@PathVariable Integer id) {
        return enrollmentsService.deleteEnrollment(id);
    }

    @GetMapping
    public ResponseBase findAllEnrollments() {
        return enrollmentsService.findAllEnrollments();
    }

    @GetMapping("/{id}")
    public ResponseBase findOneEnrollment(@PathVariable Integer id) {
        return enrollmentsService.findOneEnrollment(id);
    }

    @PatchMapping("/{id}")
    public ResponseBase updateEnrollment(@PathVariable Integer id, @RequestBody RequestEnrollment requestEnrollment) {
        return enrollmentsService.updateEnrollment(id, requestEnrollment);
    }

    @PatchMapping("/cancel")
    public ResponseBase cancelEnrollment(@RequestBody RequestCancelEnrollment requestCancelEnrollment) {
        return enrollmentsService.cancelEnrollment(requestCancelEnrollment);
    }
}
