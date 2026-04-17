package org.strawberries.userrestapiimplementation.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.strawberries.userrestapicontract.dto.UserPatchRequest;
import org.strawberries.userrestapicontract.dto.UserRequest;
import org.strawberries.userrestapicontract.dto.UserResponse;
import org.strawberries.userrestapicontract.endpoints.UserApi;

public class UserController implements UserApi {
    @Override
    public PagedModel<EntityModel<UserResponse>> getAllUsers(int page, int size) {
        return null;
    }

    @Override
    public EntityModel<UserResponse> getUserById(Long id) {
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
