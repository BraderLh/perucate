package com.balh.perucate.service;

import com.balh.perucate.agreggates.response.ResponseBase;

import java.util.Map;

public interface UsersService {
    ResponseBase signUp(Map<String,String> requestMap);
    ResponseBase login(Map<String,String> requestMap);
    ResponseBase getAllUsers();
    ResponseBase getUser(String email);
    ResponseBase deleteUser(int id);
}
