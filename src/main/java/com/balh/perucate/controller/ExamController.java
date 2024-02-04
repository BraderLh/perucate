package com.balh.perucate.controller;

import com.balh.perucate.agreggates.request.RequestExam;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.ExamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseBase createScore(@RequestBody RequestExam requestExam) {
        return examService.createExam(requestExam);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteScore(@PathVariable Integer id) {
        return examService.deleteExam(id);
    }

    @GetMapping
    public ResponseBase findAllScores() {
        return examService.findAllExams();
    }

    @GetMapping("/{id}")
    public ResponseBase findOneScore(@PathVariable Integer id) {
        return examService.findOneExam(id);
    }

    @PatchMapping("/{id}")
    public ResponseBase updateStudent(@PathVariable Integer id, @RequestBody RequestExam requestExam) {
        return examService.updateExam(id, requestExam);
    }
}
