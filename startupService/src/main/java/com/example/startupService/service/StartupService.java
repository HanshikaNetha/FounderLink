package com.example.startupService.service;

import com.example.startupService.dto.*;
import com.example.startupService.entity.Startup;
import com.example.startupService.enums.ApprovalStatus;
import com.example.startupService.exception.StartupNotFoundException;
import com.example.startupService.exception.UnauthorizedException;
import com.example.startupService.feign.UserClient;
import com.example.startupService.repository.StartupRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StartupService {
    private final StartupRepository startupRepository;
    private final ModelMapper modelMapper;
    private final UserClient userClient;

    public StartupResponse createStartup(StartupCreateRequest request) {
        Long founderId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponse userResponse=userClient.getUserById(founderId);
        String role = userResponse.getRole().toString();
        if (!role.equalsIgnoreCase("ROLE_FOUNDER")) {
            throw new UnauthorizedException("Only founders can create startups");
        }
        Startup startup = modelMapper.map(request, Startup.class);
        startup.setFounderId(founderId);
        startup.setApprovalStatus(ApprovalStatus.PENDING);
        startup.setCreatedAt(LocalDateTime.now());
        startup.setUpdatedAt(LocalDateTime.now());
        Startup savedStartup = startupRepository.save(startup);
        StartupResponse response = modelMapper.map(savedStartup, StartupResponse.class);
        response.setMessage("startup "+response.getStartupName()+" is created");
        return response;
    }
    public PageResponse<StartupResponse> getAllStartupsPage(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Startup> startupPage = startupRepository.findAll(pageable);
        List<StartupResponse> content = startupPage.getContent().stream().map(startup -> modelMapper.map(startup, StartupResponse.class)).toList();
        return new PageResponse<>(content, startupPage.getNumber(), startupPage.getSize(), startupPage.getTotalElements(), startupPage.getTotalPages());
    }

    public StartupResponse getStartupById(Long id) {
        Startup startup = startupRepository.findById(id).orElseThrow(() -> new StartupNotFoundException("Startup not found"));
        StartupResponse response= modelMapper.map(startup, StartupResponse.class);
        return response;
    }


}
