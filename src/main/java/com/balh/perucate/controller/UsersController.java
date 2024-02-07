package com.balh.perucate.controller;

import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/singUp")
    public ResponseBase singUp(@RequestBody Map<String,String> requestMap) {
        return usersService.signUp(requestMap);
    }

    @PostMapping("/login")
    public ResponseBase login(@RequestBody Map<String,String> requestMap){
        return usersService.login(requestMap);
    }

    @GetMapping
    public ResponseBase getAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping("/{email}")
    public ResponseBase getUser(@PathVariable String email) {
        return usersService.getUser(email);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteUser(@PathVariable int id) {
        return usersService.deleteUser(id);
    }
}
