package org.strawberries.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.strawberries.userservice.security.AppUserDetails;

@RestController
@RequiredArgsConstructor
public class UserController1 {

    // ✅ Доступно и ADMIN, и USER — видят свой профиль
    @GetMapping("/api/users/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getMe(@AuthenticationPrincipal AppUserDetails principal) {
        return ResponseEntity.ok(EntityModel.of(principal.getUser()));
    }

    // ❌ Только ADMIN — список всех пользователей
    @GetMapping("/api/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        // return userService.findAll() ...
        return ResponseEntity.ok("Список пользователей (только для ADMIN)");
    }

    // ❌ Только ADMIN — удаление пользователя (POST → CSRF защита активна)
    @PostMapping("/api/admin/users/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateUser(@PathVariable String id) {
        // userService.deactivate(id)
        return ResponseEntity.ok("Пользователь деактивирован");
    }
}