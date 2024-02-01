package com.balh.perucate.service;

import com.balh.perucate.agreggates.request.RequestScore;
import com.balh.perucate.agreggates.response.ResponseBase;

public interface ScoreService {
    ResponseBase createScore(RequestScore requestScore);
    ResponseBase deleteScore(Integer id);
    ResponseBase findOneScore(Integer id);
    ResponseBase findAllScores();
    ResponseBase updateScore(Integer id, RequestScore requestScore);
}
