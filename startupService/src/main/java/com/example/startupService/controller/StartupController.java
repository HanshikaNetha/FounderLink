package com.example.startupService.controller;

import com.example.startupService.dto.PageResponse;
import com.example.startupService.dto.StartupCreateRequest;
import com.example.startupService.dto.StartupResponse;
import com.example.startupService.dto.StartupUpdateRequest;
import com.example.startupService.service.StartupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/startups")
@RestController
@RequiredArgsConstructor
public class StartupController {
    private final StartupService startupService;

    @PostMapping("/createStartup")
    @PreAuthorize("hasAuthority('ROLE_FOUNDER')")
    public ResponseEntity<StartupResponse> createStartup(@Valid @RequestBody StartupCreateRequest request) {
        StartupResponse response = startupService.createStartup(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllStartups")
    public ResponseEntity<PageResponse<StartupResponse>> getAllStartupsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "startupId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        PageResponse<StartupResponse> response = startupService.getAllStartupsPage(page, size, sortBy, direction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getStartupById/{id}")
    public ResponseEntity<StartupResponse> getStartupById(@PathVariable Long id) {
        StartupResponse response=startupService.getStartupById(id);
        return ResponseEntity.ok(response);
    }


}
