package com.vietcuong.simpleSecurity.controller;


import com.vietcuong.simpleSecurity.common.ApplicationError;
import com.vietcuong.simpleSecurity.entity.User;
import com.vietcuong.simpleSecurity.response.GlobalResponse;
import com.vietcuong.simpleSecurity.service.AuthenticationService;
import com.vietcuong.simpleSecurity.service.TokenService;
import com.vietcuong.simpleSecurity.service.UserDetailsService;
import com.vietcuong.simpleSecurity.service.UserService;
import com.vietcuong.simpleSecurity.validator.ObjectValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class AuthenticationController {

    private final AuthenticationService authenticationService;


    private final UserService userService;

    private final TokenService tokenService;

    private final GlobalResponse<?> globalResponse;

    private final ObjectValidator<User> objectValidator;

    public AuthenticationController(AuthenticationService authenticationService,
                                    UserService userService,
                                    UserDetailsService userDetailsService,
                                    TokenService tokenService,
                                    GlobalResponse<?> globalResponse,
                                    ObjectValidator<User> objectValidator) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.globalResponse = globalResponse;
        this.objectValidator = objectValidator;
    }


    @PostMapping("/register")
    public GlobalResponse<?> register(
            @RequestBody
            User userRequest) {
        return globalResponse.initializeResponse(
                ApplicationError.GlobalError.USER_REGISTRATION_SUCCESS,
                authenticationService.register(userRequest));
    }

    /*    @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody User userRequest) {
            if (!userService.existsByUsername(userRequest.getUsername())) {
                return new ResponseEntity<String>("Incorrect username",
                        HttpStatus.BAD_REQUEST);
            }
            if (!userService.checkPassword(userRequest.getUsername(),
                    userRequest.getPassword())) {
                return new ResponseEntity<String>("Incorrect password",
                        HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(
                    authenticationService.authenticationResponse(userRequest));
        }*/
    @PostMapping("/login")
    public GlobalResponse<?> login(
            @RequestBody
            User userRequest) {
        if (!userService.existsByUsername(userRequest.getUsername())) {
            return globalResponse.createMessageResponse(
                    ApplicationError.GlobalError.USER_REGISTRATION_ERROR, "error",
                    "Incorrect username");

        }
        if (!userService.checkPassword(userRequest.getUsername(),
                                       userRequest.getPassword())) {
            return globalResponse.createMessageResponse(
                    ApplicationError.GlobalError.USER_REGISTRATION_ERROR, "error",
                    "Incorrect password");
        }
        return globalResponse.initializeResponse(
                ApplicationError.GlobalError.USER_REGISTRATION_SUCCESS,
                authenticationService.login(userRequest));
    }


    @GetMapping("/getAllUsers")
    public GlobalResponse<List<User>> getAllUser() {
        List<User> userList = userService.getAllUser();
        return globalResponse.initializeResponse(userList);
    }

    @GetMapping("/getAllTokens")
    public GlobalResponse<List<String>> getAllToken() {
        List<String> tokenList = tokenService.allTokensToString();
        return globalResponse.initializeResponse(tokenList);
    }

    @DeleteMapping("/admin/deleteUser")
    public GlobalResponse<?> deleteUser(
            @RequestBody
            User userRequest) {
        if (!userService.existsByUsername(userRequest.getUsername())) {
            return globalResponse.createMessageResponse(
                    ApplicationError.GlobalError.USER_REGISTRATION_ERROR, "error",
                    "User doesn't exist");
        }
        return globalResponse.createMessageResponse(
                ApplicationError.GlobalError.USER_REGISTRATION_ERROR, "message",
                "User deleted successfully");
    }


}
