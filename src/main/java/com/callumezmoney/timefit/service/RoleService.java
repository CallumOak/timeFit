package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Role;

public interface RoleService {
    Role getRole(String roleName);
    Role getRole(Long id);
}
