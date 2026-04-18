package org.strawberries.userservice.mapper;

import org.mapstruct.*;
import org.strawberries.userapi.dto.UserPatchRequest;
import org.strawberries.userapi.dto.UserRequest;
import org.strawberries.userapi.dto.UserResponse;
import org.strawberries.userservice.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponse toResponse(User user);

    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "role", constant = "USER")
    User toEntity(UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(UserPatchRequest request, @MappingTarget User user);

    void update(UserRequest request, @MappingTarget User user);
}