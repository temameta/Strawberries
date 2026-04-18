package org.strawberries.userservice.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.strawberries.userapi.dto.PagedResponse;
import org.strawberries.userapi.dto.UserPatchRequest;
import org.strawberries.userapi.dto.UserRequest;
import org.strawberries.userapi.dto.UserResponse;
import org.strawberries.userapi.exception.AttributeAlreadyExistsException;
import org.strawberries.userapi.exception.UserNotFoundException;
import org.strawberries.userservice.entity.User;
import org.strawberries.userservice.mapper.UserMapper;
import org.strawberries.userservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponse create(UserRequest request) {
        if (request.email() != null && repository.existsByEmailAndActiveTrue(request.email())) throw new AttributeAlreadyExistsException("email", request.email());
        if (repository.existsByPhoneAndActiveTrue(request.phone())) throw new AttributeAlreadyExistsException("phone", request.phone());
        User newUser = mapper.toEntity(request);
        // ВРЕМЕННО
        newUser.setPasswordHash(request.password() + "_HASH");
        return mapper.toResponse(repository.save(newUser));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<UserResponse> findAll(int page, int size, Boolean isActive) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = isActive == null ? repository.findAll(pageable) : repository.findAllByActive(pageable, isActive);
        List<UserResponse> content = userPage.getContent().stream()
                .map(mapper::toResponse)
                .toList();
        return new PagedResponse<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(UUID id, boolean onlyActive) {
        Optional<User> user = onlyActive ? repository.findByIdAndActiveTrue(id) : repository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException(id);
        return mapper.toResponse(user.get());
    }

    @Override
    @Transactional
    public UserResponse update(UUID id, UserRequest request) {
        User user = repository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (!user.getEmail().equals(request.email()) && repository.existsByEmailAndActiveTrue(request.email()))
            throw new AttributeAlreadyExistsException("email", request.email());
        if (!user.getPhone().equals(request.phone()) && repository.existsByPhoneAndActiveTrue(request.phone()))
            throw new AttributeAlreadyExistsException("phone", request.phone());
        mapper.update(request, user);
        user.setPasswordHash(request.password() + "_HASH");
        return mapper.toResponse(repository.save(user));
    }

    @Override
    @Transactional
    public UserResponse patch(UUID id, UserPatchRequest request) {
        User user = repository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (request.email() != null && !request.email().equals(user.getEmail()))
            if (repository.existsByEmailAndActiveTrue(request.email()))
                throw new AttributeAlreadyExistsException("email", request.email());
        if (request.phone() != null && !request.phone().equals(user.getPhone()))
            if (repository.existsByPhoneAndActiveTrue(request.phone()))
                throw new AttributeAlreadyExistsException("phone", request.phone());
        mapper.patch(request, user);
        return mapper.toResponse(repository.save(user));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        User user = repository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(false);
        repository.save(user);
    }
}
