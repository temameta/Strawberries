package org.strawberries.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.strawberries.userapi.dto.Role;
import org.strawberries.userapi.dto.UserRequest;
import org.strawberries.userapi.dto.UserResponse;
import org.strawberries.userservice.entity.User;
import org.strawberries.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    // create findbyid findall delete patch update

    public UserResponse create(UserRequest request) {
        repository.save(User.builder()
                            .email(request.email())
                            .firstName(request.firstName())
                            .phone(request.phone())
                            .role(request.role() == null ? Role.USER : Role.ADMIN)
                            .isActive(true)
                            .build());
        return UserResponse.builder().id(request.).email().firstName().phone().role().isActive().createdAt().updatedAt().build();
    }
}
