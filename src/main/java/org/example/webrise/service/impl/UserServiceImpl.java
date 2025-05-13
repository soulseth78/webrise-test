package org.example.webrise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webrise.dto.CreateUserRequest;
import org.example.webrise.dto.UserDto;
import org.example.webrise.entity.User;
import org.example.webrise.exception.NotFoundException;
import org.example.webrise.mapper.UserMapper;
import org.example.webrise.repository.UserRepository;
import org.example.webrise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(CreateUserRequest request) {
        log.debug("Creating user with data: {}", request);
        User user = userMapper.toEntity(request);
        log.info("User created with id: {}", user.getId());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(UUID id) {
        log.debug("Fetching user with id: {}", id);
        return userMapper.toDto(findById(id));
    }

    @Override
    public UserDto updateUser(UUID id, CreateUserRequest request) {
        log.debug("Updating user with id: {}, new data: {}", id, request);
        User user = findById(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        log.info("User updated with id: {}", user.getId());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(UUID id) {
        log.debug("Deleting user with id: {}", id);
        User user = findById(id);
        userRepository.delete(user);
        log.info("User deleted with id: {}", id);
    }

    private User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new NotFoundException("User not found with id: " + id);
                });
    }
}
