package org.example.webrise.mapper;

import org.example.webrise.dto.CreateUserRequest;
import org.example.webrise.dto.UserDto;
import org.example.webrise.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest dto);

    UserDto toDto(User user);
}
