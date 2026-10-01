package org.strawberries.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal")
public class InternalController {

    // ✅ Доступно клиенту с ролью SERVICE_USER и SERVICE_ADMIN
    @GetMapping("/health")
    @PreAuthorize("hasRole('SERVICE_USER')")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK — service user access granted");
    }

    // ✅ Только SERVICE_ADMIN
    @GetMapping("/admin/stats")
    @PreAuthorize("hasRole('SERVICE_ADMIN')")
    public ResponseEntity<String> adminStats() {
        return ResponseEntity.ok("Stats — admin access granted");
    }
}