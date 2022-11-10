package com.wezaam.withdrawal.rest;

import com.wezaam.withdrawal.model.User;
import com.wezaam.withdrawal.rest.response.ResponseConverter;
import com.wezaam.withdrawal.rest.response.UserResponse;
import com.wezaam.withdrawal.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseConverter responseConverter;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userService.findAll();
        List<UserResponse> responses = users.stream()
                                            .map(user -> responseConverter.convertFromUser(user))
                                            .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserResponse response = responseConverter.convertFromUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
