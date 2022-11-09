package com.wezaam.withdrawal.rest;

import com.wezaam.withdrawal.model.User;
import com.wezaam.withdrawal.repository.UserRepository;
import com.wezaam.withdrawal.rest.response.ApiResponsesConverter;
import com.wezaam.withdrawal.rest.response.UserResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiResponsesConverter objectsConverter;

    @GetMapping("/find-all-users")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = users.stream()
                                            .map(user -> objectsConverter.convertFromUser(user))
                                            .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/find-user-by-id/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserResponse response = objectsConverter.convertFromUser(user.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.notFound()
                             .build();
    }
}
