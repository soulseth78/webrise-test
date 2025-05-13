package org.example.webrise.controller;

import lombok.RequiredArgsConstructor;
import org.example.webrise.dto.CreateUserRequest;
import org.example.webrise.dto.UserDto;
import org.example.webrise.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable UUID id, @RequestBody CreateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

}
