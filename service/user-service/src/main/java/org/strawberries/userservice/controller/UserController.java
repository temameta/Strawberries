package org.strawberries.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.strawberries.userapi.dto.PagedResponse;
import org.strawberries.userapi.dto.UserPatchRequest;
import org.strawberries.userapi.dto.UserRequest;
import org.strawberries.userapi.dto.UserResponse;
import org.strawberries.userapi.endpoints.UserApi;
import org.strawberries.userservice.assembler.UserModelAssembler;
import org.strawberries.userservice.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService service;
    private final UserModelAssembler modelAssembler;
    private final PagedResourcesAssembler<UserResponse> pagedUsersAssembler;

    @Override
    public PagedModel<EntityModel<UserResponse>> getAllUsers(int page, int size) {
        PagedResponse<UserResponse> pagedResponse = service.findAll(page, size, true);
        Page<UserResponse> springPage = new PageImpl<>(
                pagedResponse.content(),
                PageRequest.of(pagedResponse.pageNumber(), pagedResponse.pageSize()),
                pagedResponse.totalElements()
        );
        return pagedUsersAssembler.toModel(springPage, modelAssembler);
    }

    @Override
    public EntityModel<UserResponse> getUserById(UUID id) {
        return modelAssembler.toModel(service.findById(id, true));
    }

    @Override
    public ResponseEntity<EntityModel<UserResponse>> createUser(UserRequest request) {
        UserResponse created = service.create(request);
        EntityModel<UserResponse> entityModel = modelAssembler.toModel(created);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<UserResponse> updateUser(UUID id, UserRequest request) {
        return modelAssembler.toModel(service.update(id, request));
    }

    @Override
    public EntityModel<UserResponse> patchUser(UUID id, UserPatchRequest request) {
        return modelAssembler.toModel(service.patch(id, request));
    }

    @Override
    public EntityModel<UserResponse> deleteUserById(UUID id) {
        return modelAssembler.toModel(service.delete(id));
    }
}
