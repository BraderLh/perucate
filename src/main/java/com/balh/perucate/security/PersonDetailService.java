package com.balh.perucate.security;

import com.balh.perucate.entity.UsersEntity;
import com.balh.perucate.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class PersonDetailService  implements UserDetailsService {
    private final UsersRepository usersRepository;
    UsersEntity usersEntity;

    public PersonDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Login as loadUserByUsername: " + username);
        usersEntity = usersRepository.findByEmail(username);
        if (!Objects.isNull(usersEntity)) {
            return new User(usersEntity.getEmail(),usersEntity.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UsersEntity getUsers() {
        return usersEntity;
    }
}
