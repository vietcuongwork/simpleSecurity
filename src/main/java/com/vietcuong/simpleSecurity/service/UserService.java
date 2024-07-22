package com.vietcuong.simpleSecurity.service;


import com.vietcuong.simpleSecurity.config.SecurityConfig;
import com.vietcuong.simpleSecurity.entity.User;
import com.vietcuong.simpleSecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final SecurityConfig securityConfig;

    public UserService(UserRepository userRepository,
                       UserDetailsService userDetailsService,
                       SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.securityConfig = securityConfig;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkPassword(String username, String rawPassword) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        return securityConfig.passwordEncoder()
                .matches(rawPassword, user.getPassword());
    }


}
