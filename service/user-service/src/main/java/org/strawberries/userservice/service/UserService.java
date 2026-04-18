package org.strawberries.userservice.service;

import org.strawberries.userapi.dto.PagedResponse;
import org.strawberries.userapi.dto.UserPatchRequest;
import org.strawberries.userapi.dto.UserRequest;
import org.strawberries.userapi.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse create(UserRequest request);
    PagedResponse<UserResponse> findAll(int page, int size, Boolean isActive);
    UserResponse findById(UUID id, boolean onlyActive);
    UserResponse update(UUID id, UserRequest request);
    UserResponse patch(UUID id, UserPatchRequest request);
    UserResponse delete(UUID id);
}
