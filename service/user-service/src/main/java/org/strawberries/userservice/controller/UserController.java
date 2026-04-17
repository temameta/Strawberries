package org.strawberries.userservice.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.strawberries.userapi.dto.UserPatchRequest;
import org.strawberries.userapi.dto.UserRequest;
import org.strawberries.userapi.dto.UserResponse;
import org.strawberries.userapi.endpoints.UserApi;

import java.util.UUID;

public class UserController implements UserApi {
    @Override
    public PagedModel<EntityModel<UserResponse>> getAllUsers(int page, int size) {
        return null;
    }

    @Override
    public EntityModel<UserResponse> getUserById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<UserResponse>> createUser(UserRequest request) {
        return null;
    }

    @Override
    public EntityModel<UserResponse> updateUser(Long id, UserRequest request) {
        return null;
    }

    @Override
    public EntityModel<UserResponse> patchUser(Long id, UserPatchRequest request) {
        return null;
    }

    @Override
    public EntityModel<UserResponse> deleteUserById(Long id) {
        return null;
    }
}
