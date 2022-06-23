package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.UserRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Disabled
class UserServiceImplTest {
    public static final String CALLUM = "callum";
    public static final String CALLUM_EMAIL = CALLUM + "@cal.lum";
    public static final String CALLUM_PASSWORD = CALLUM + "123";
    public static final String LELE = "lele";
    private User userCallum;
    private User userLele;
    private List<User> users;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        Role role = new Role(1L, RoleEnum.ROLE_USER);
        userCallum = new User(1L, CALLUM,
                CALLUM_EMAIL,
                CALLUM_PASSWORD,
                null,null,null,
                role);
        userLele = new User(2L, LELE,
                LELE + "@le.le",
                LELE + "123",
                null,null,null,
                role);
        users = List.of(userCallum, userLele);
    }

    @Test
    void getAll() {
        doReturn(users).when(userRepository).findAll();

        List<User> u = userService.getAll();

        assertNotNull(u);
        assertEquals(2, u.size());
    }

    @Test
    void getUserEmailPassword() {
        doReturn(Optional.of(userCallum)).when(userRepository).findByEmailAndPassword(CALLUM_EMAIL, CALLUM_PASSWORD);

        User user = userService.getUser(CALLUM_EMAIL, CALLUM_PASSWORD).get();

        assertNotNull(user);
        assertEquals(CALLUM_EMAIL, user.getEmail());
    }

    @Test
    void getUserUsername() {
        doReturn(Optional.of(userCallum)).when(userRepository).findByUsername(CALLUM);

        User user = userService.getUser(CALLUM).get();

        assertNotNull(user);
        assertEquals(CALLUM_EMAIL, user.getEmail());
    }

    @Test
    void getUserId() {
        doReturn(Optional.of(userCallum)).when(userRepository).findById(1L);

        User user = userService.getUser(1L).get();

        assertNotNull(user);
        assertEquals(CALLUM_EMAIL, user.getEmail());
    }

    @Test
    void addUser() {
        doReturn(userLele).when(userRepository).save(userLele);

        User user = userService.addUser(userLele);

        assertNotNull(user);
        assertEquals(LELE, user.getUsername());

    }

    @Test
    void updateUser() {
        userService.updateUser(userLele);

        verify(userRepository).save(userLele);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(userLele.getId());

        verify(userRepository).deleteById(userLele.getId());
    }
}