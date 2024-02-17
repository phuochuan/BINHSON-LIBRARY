package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.dto.BorrowPermissionRequest;
import com.library.binhson.userservice.dto.ObjectPage;
import com.library.binhson.userservice.dto.PermissionRequestDto;
import com.library.binhson.userservice.entity.*;
import com.library.binhson.userservice.repository.CitizenIdentityCardRepository;
import com.library.binhson.userservice.repository.PermissionRequestRepository;
import com.library.binhson.userservice.repository.UserRepository;
import com.library.binhson.userservice.service.IPermissionService;
import com.library.binhson.userservice.service.third_party_system.KeycloakService;
import com.library.binhson.userservice.service.third_party_system.PermissionKeycloakService;
import com.library.binhson.userservice.ultils.AuthMyInfoUtils;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {
    private final PermissionRequestRepository permissionRequestRepository;
    private final CitizenIdentityCardRepository citizenIdentityCardRepository;
    private final UserRepository userRepository;
    private final KeycloakService keycloakService ;
    private final ModelMapper modelMapper;
    private final PermissionKeycloakService permissionKeycloakService;
    @Override
    @CacheEvict(value = "permission_requests")
    public void requestBorrowPermission(BorrowPermissionRequest permissionRequest) {
        var username= AuthMyInfoUtils.getUsername();
        var userId=keycloakService.getIdByUsername(username);
        var currentUser=userRepository.findById(userId).orElseThrow(()->new RuntimeException());
        if(permissionRequestRepository.existsByUser(currentUser))
        {
            List<PermissionRequest> pastRequest=permissionRequestRepository.findByUser(currentUser);
            if(pastRequest.stream().anyMatch(pr->
                (pr.getStatus().equals(Status.ACCEPTED) ||pr.getStatus().equals(Status.WAITING))
                        && pr.getRequestPermission().equals(Permission.borrow)))
                throw new BadRequestException("");
        }

        if(!citizenIdentityCardRepository.existsByUser(currentUser)){
            var identity=modelMapper.map(permissionRequest, CitizenIdentityCard.class);
            citizenIdentityCardRepository.save(identity);
            currentUser.setCitizenIdentityCard(identity);
            userRepository.save(currentUser);
            identity.setUser(currentUser);
            citizenIdentityCardRepository.save(identity);


        }
        var request= PermissionRequest.builder()
                .requestPermission(Permission.borrow)
                .date(new Date())
                .role(Role.MEMBER)
                .user(currentUser)
                .status(Status.WAITING)
                .build();
        permissionRequestRepository.save(request);
    }



    @Override
    public List<PermissionRequestDto> getPermission(int page, int size) {
        List<PermissionRequestDto> permissionRequests=getAll();
        ObjectPage objectPage=new ObjectPage(permissionRequests,page,size);
        return (List<PermissionRequestDto>) objectPage.getCurrentPage();
    }

    @Override
    public List<PermissionRequestDto> getPermission(String userId) {
        var permissionRequests=getAll();
        return permissionRequests.stream().filter(pr-> pr.getUserId().equals(userId)).toList();
    }

    @Override
    @CacheEvict(value = "permission_requests")
    public void accept(Long id) {
        var foundRequest=permissionRequestRepository.findById(id).orElseThrow(()->new NotFoundException());
        foundRequest.setStatus(Status.ACCEPTED);
        permissionKeycloakService.allowBorrow(foundRequest.getUser().getId());
        permissionRequestRepository.save(foundRequest);
    }

    @Override
    @CacheEvict(value = "permission_requests")
    public void gantBorrowPermission(String userId) {
        permissionKeycloakService.allowBorrow(userId);
    }

    @Cacheable(value = "permission_requests")
    private List<PermissionRequestDto> getAll() {
        var requests=permissionRequestRepository.findAll();
        return requests.stream().map(permissionRequest -> modelMapper.map(permissionRequest, PermissionRequestDto.class)).collect(Collectors.toList());
    }
}
