package com.balh.perucate.controller;

import com.balh.perucate.agreggates.request.RequestScore;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.ScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/score")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseBase createScore(@RequestBody RequestScore requestScore) {
        return scoreService.createScore(requestScore);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteScore(@PathVariable Integer id) {
        return scoreService.deleteScore(id);
    }

    @GetMapping
    public ResponseBase findAllScores() {
        return scoreService.findAllScores();
    }

    @GetMapping("/{id}")
    public ResponseBase findOneScore(@PathVariable Integer id) {
        return scoreService.findOneScore(id);
    }

    @PatchMapping("/{id}")
    public ResponseBase updateStudent(@PathVariable Integer id, @RequestBody RequestScore requestScore) {
        return scoreService.updateScore(id, requestScore);
    }
}
