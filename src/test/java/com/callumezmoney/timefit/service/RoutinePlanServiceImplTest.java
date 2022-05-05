package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.*;
import com.callumezmoney.timefit.repository.RoutinePlanRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static com.callumezmoney.timefit.bootstrapper.Bootstrapper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoutinePlanServiceImplTest {
    public static final String CALLUM = "callum";
    @Mock
    RoutinePlanRepository routinePlanRepository;
    @InjectMocks
    RoutinePlanServiceImpl routinePlanService;
    Program programCallum;
    List<RoutinePlan> routinePlans;
    private User userCallum;

    @BeforeEach
    void setUp() throws ParseException {
        routinePlans = createRoutinePlans(createRoutines(createExercises(true), true), true);
        programCallum = createPrograms(routinePlans, true).get(0);
        Role role = new Role(1L, RoleEnum.ROLE_USER);
        userCallum = new User(1L, CALLUM,
                CALLUM + "@cal.lum",
                CALLUM + "123",
                null,null,null,
                role);

        addRoutinePlansToPrograms(routinePlans, List.of(programCallum));
        programCallum.setUser(userCallum);
    }

    @Test
    void getRoutines() {
        doReturn(routinePlans).when(routinePlanRepository).findAll();

        List<RoutinePlan> rps = routinePlanService.getRoutines();
        assertNotNull(rps);
        assertEquals(7, rps.size());
    }

    @Test
    void getRoutinePlanWithUsername() {
        doReturn(Optional.of(routinePlans.get(0))).when(routinePlanRepository).findById(1L);

        RoutinePlan rp = routinePlanService.getRoutinePlan(1L, CALLUM).get();

        assertNotNull(rp);
        assertEquals(programCallum, rp.getProgram());
        assertEquals(CALLUM, rp.getProgram().getUser().getUsername());
    }

    @Test
    void getRoutinePlanWithoutUsername() {
        doReturn(Optional.of(routinePlans.get(0))).when(routinePlanRepository).findById(1L);

        RoutinePlan rp = routinePlanService.getRoutinePlan(1L).get();

        assertNotNull(rp);
        assertEquals(programCallum, rp.getProgram());
        assertEquals(CALLUM, rp.getProgram().getUser().getUsername());
    }

    @Test
    void editRoutinePlan() {
        doReturn(Optional.of(routinePlans.get(0))).when(routinePlanRepository).findById(1L);

        routinePlanService.editRoutinePlan(routinePlans.get(0), CALLUM);

        verify(routinePlanRepository).findById(1L);
    }

    @Test
    void createRoutinePlan() {
        doReturn(routinePlans.get(0)).when(routinePlanRepository).save(routinePlans.get(0));

        RoutinePlan rp = routinePlanService.createRoutinePlan(routinePlans.get(0), CALLUM).get();
        assertNotNull(rp);
    }

    @Test
    void deleteRoutinePlan() {
        doReturn(Optional.of(routinePlans.get(0))).when(routinePlanRepository).findById(1L);

        routinePlanService.deleteRoutinePlan(1L, CALLUM);

        verify(routinePlanRepository).findById(1L);
        verify(routinePlanRepository).deleteById(1L);
    }
}