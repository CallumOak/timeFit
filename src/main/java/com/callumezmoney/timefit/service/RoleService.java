package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Role;

import java.util.Optional;

public interface RoleService {
    Role getRole(String roleName);
    Role getRole(Long id);
}
