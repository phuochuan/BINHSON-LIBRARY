package com.library.binhson.userservice.service;

import com.library.binhson.userservice.dto.BorrowPermissionRequest;
import com.library.binhson.userservice.dto.PermissionRequestDto;

import java.util.List;

public interface IPermissionService {
    void requestBorrowPermission(BorrowPermissionRequest permissionRequest);

    List<PermissionRequestDto> getPermission(int page, int size);

    List<PermissionRequestDto> getPermission(String userId);

    void accept(Long id);

    void gantBorrowPermission(String userId);
}
