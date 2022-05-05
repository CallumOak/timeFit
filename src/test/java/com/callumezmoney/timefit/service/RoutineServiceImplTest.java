package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.RoutineRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.callumezmoney.timefit.bootstrapper.Bootstrapper.createExercises;
import static com.callumezmoney.timefit.bootstrapper.Bootstrapper.createRoutines;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoutineServiceImplTest {
    public static final String CALLUM = "callum";
    @Mock
    RoutineRepository routineRepository;
    @Mock
    UserService userService;
    @InjectMocks
    RoutineServiceImpl routineService;
    List<Routine> routines;
    private User userCallum;

    @BeforeEach
    void setUp() {
        routines = createRoutines(createExercises(true), true);
        Role role = new Role(1L, RoleEnum.ROLE_USER);
        userCallum = new User(1L, CALLUM,
                CALLUM + "@cal.lum",
                CALLUM + "123",
                null,null,null,
                role);


        routines.stream().forEach(r -> r.setUser(userCallum));
    }

    @Test
    void getRoutines() {
        doReturn(routines).when(routineRepository).findAllByUser(userCallum);
        doReturn(Optional.of(userCallum)).when(userService).getUser(CALLUM);
        List<Routine> routines = routineService.getRoutines(CALLUM);
        assertNotNull(routines);
        assertEquals(3, routines.size());
    }

    @Test
    void getRoutine() {
        doReturn(Optional.of(routines.get(0))).when(routineRepository).findById(1L);

        Routine routine = routineService.getRoutine(1L).get();
        assertNotNull(routine);
        assertEquals(CALLUM, routine.getUser().getUsername());
    }

    @Test
    void getRoutineWithUsername() {
        doReturn(Optional.of(userCallum)).when(userService).getUser(CALLUM);
        doReturn(Optional.of(routines.get(0))).when(routineRepository).findByIdAndUser(1L, userCallum);

        Routine routine = routineService.getRoutine(1L, CALLUM).get();
        assertNotNull(routine);
        assertEquals(CALLUM, routine.getUser().getUsername());
    }

    @Test
    void createRoutine() {
        doReturn(routines.get(0)).when(routineRepository).save(routines.get(0));

        Routine routine = routineService.createRoutine(routines.get(0), CALLUM).get();
        assertNotNull(routine);
        assertEquals(CALLUM, routine.getUser().getUsername());
    }

    @Test
    void updateRoutine() {
        doReturn(Optional.of(routines.get(0))).when(routineRepository).findById(1L);
        doReturn(routines.get(0)).when(routineRepository).save(routines.get(0));

        routineService.updateRoutine(routines.get(0), CALLUM);

        verify(routineRepository).findById(1L);
        verify(routineRepository).save(routines.get(0));
    }

    @Test
    void deleteRoutine() {
        doReturn(Optional.of(routines.get(0))).when(routineRepository).findById(1L);

        routineService.deleteRoutine(1L, CALLUM);

        verify(routineRepository).findById(1L);
        verify(routineRepository).deleteById(1L);
    }
}