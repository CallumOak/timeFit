package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.repository.RoleRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@Disabled
class RoleServiceImplTest {
    private final String ADMIN = "admin";
    private final String MODERATOR = "mod";
    private final String USER = "user";
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleServiceImpl roleService;
    static Role roleAdmin;
    static Role roleModerator;
    static Role roleUser;

    @BeforeAll
    static void init() {
        roleAdmin = new Role(
                1L,
                RoleEnum.ROLE_ADMIN
        );
        roleModerator = new Role(
                2L,
                RoleEnum.ROLE_MODERATOR
        );
        roleUser = new Role(
                3L,
                RoleEnum.ROLE_USER
        );
    }

    @Test
    void getRole() {
        doReturn(Optional.of(roleAdmin)).when(roleRepository).findById(1L);

        Role role = roleService.getRole(1L);

        assertNotNull(role);
        assertEquals(role.getName(), RoleEnum.ROLE_ADMIN);
    }

    @Test
    void getRoleAdmin() {
        doReturn(Optional.of(roleAdmin)).when(roleRepository).findByName(RoleEnum.ROLE_ADMIN);

        Role role = roleService.getRole(ADMIN);

        assertNotNull(role);
        assertEquals(role.getName(), RoleEnum.ROLE_ADMIN);
    }

    @Test
    void getRoleModerator() {
        doReturn(Optional.of(roleModerator)).when(roleRepository).findByName(RoleEnum.ROLE_MODERATOR);

        Role role = roleService.getRole(MODERATOR);

        assertNotNull(role);
        assertEquals(role.getName(), RoleEnum.ROLE_MODERATOR);
    }

    @Test
    void getRoleUser() {
        doReturn(Optional.of(roleUser)).when(roleRepository).findByName(RoleEnum.ROLE_USER);

        Role role = roleService.getRole(USER);

        assertNotNull(role);
        assertEquals(role.getName(), RoleEnum.ROLE_USER);
    }
}