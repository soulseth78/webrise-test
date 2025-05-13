package org.example.webrise.service;

import org.example.webrise.dto.CreateUserRequest;
import org.example.webrise.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto createUser(CreateUserRequest request);
    UserDto getUserById(UUID id);
    UserDto updateUser(UUID id, CreateUserRequest request);
    void deleteUser(UUID id);
}
