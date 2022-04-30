package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.repository.RoleRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    @Override
    public Role getRole(String roleName) {
        switch (roleName) {
            case "admin":
                return roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            case "mod":
                return roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            default:
                return roleRepository.findByName(RoleEnum.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }
    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).get();
    }
}
