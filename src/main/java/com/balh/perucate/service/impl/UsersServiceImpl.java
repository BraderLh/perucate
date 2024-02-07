package com.balh.perucate.service.impl;

import com.balh.perucate.agreggates.constants.Constants;
import com.balh.perucate.agreggates.response.ResponseBase;
import com.balh.perucate.entity.UsersEntity;
import com.balh.perucate.repository.UsersRepository;
import com.balh.perucate.security.PersonDetailService;
import com.balh.perucate.security.jwt.JwtUtil;
import com.balh.perucate.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final PersonDetailService personDetailService;
    private final JwtUtil jwtUtil;

    public UsersServiceImpl(UsersRepository usersRepository, AuthenticationManager authenticationManager, PersonDetailService personDetailService, JwtUtil jwtUtil) {
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.personDetailService = personDetailService;
        this.jwtUtil = jwtUtil;
    }

    private UsersEntity getUsersFromMap(Map<String, String> requestMap){
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setEmail(requestMap.get("email"));
        usersEntity.setPassword(requestMap.get("password"));
        usersEntity.setStatus(Constants.STATUS_ACTIVE);
        usersEntity.setRole(Constants.AUDIT_USER);
        usersEntity.setUserCreate(Constants.AUDIT_ADMIN);
        usersEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return usersEntity;
    }

    @Override
    public ResponseBase signUp(Map<String, String> requestMap) {
        log.info("Signing up a user: " + requestMap.toString());
        UsersEntity usersEntity = getUsersFromMap(requestMap);
        usersRepository.save(usersEntity);
        log.info("Ending signing up");
        return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(usersEntity));
    }

    @Override
    public ResponseBase login(Map<String, String> requestMap) {
        log.info("Init login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),
                            requestMap.get("password")));
            if (authentication.isAuthenticated()) {
                if (personDetailService.getUsers().getStatus() == Constants.STATUS_ACTIVE) {
                    String token = jwtUtil.generateToken(personDetailService.getUsers().getEmail(), personDetailService.getUsers().getRole());
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(token));
                }
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_LOGIN, Optional.empty());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR, Optional.of("Invalid or incorrect password"));
    }

    @Override
    public ResponseBase getAllUsers() {
        List<UsersEntity> usersEntities = usersRepository.findAll();
        if (!usersEntities.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(usersEntities));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase getUser(String email) {
        UsersEntity usersEntity = usersRepository.findByEmail(email);
        if (usersEntity != null) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(usersEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteUser(int id) {
        if (usersRepository.existsById(id)) {
            Optional<UsersEntity> usersEntityToDelete = usersRepository.findById(id);
            if (usersEntityToDelete.isPresent()) {
                usersEntityToDelete.get().setStatus(Constants.STATUS_INACTIVE);
                usersEntityToDelete.get().setDateDelete(new Timestamp(System.currentTimeMillis()));
                usersEntityToDelete.get().setUserDelete(Constants.AUDIT_ADMIN);
                usersRepository.save(usersEntityToDelete.get());
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(usersEntityToDelete));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }
}
