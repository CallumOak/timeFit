package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.*;
import com.callumezmoney.timefit.repository.ProgramRepository;
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
class ProgramServiceImplTest {
    public static final String CALLUM = "callum";
    public static final String PROGRAM_CALLUM = "Callum's program";
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private ProgramServiceImpl programService;
    private User userCallum;
    private Program programCallum;


    @BeforeEach
    void setUp() throws ParseException {
        List<RoutinePlan> routinePlans = createRoutinePlans(createRoutines(createExercises(true), true), true);
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
    void getProgram() {
        doReturn(Optional.of(programCallum)).when(programRepository).findById(1L);

        Program program = programService.getProgram(1L).get();
        assertNotNull(program);
        assertEquals(CALLUM, program.getUser().getUsername());
        assertEquals(PROGRAM_CALLUM, program.getName());
    }

    @Test
    void GetProgramWithUsername() {
        doReturn(Optional.of(userCallum)).when(userService).getUser(CALLUM);
        doReturn(Optional.of(programCallum)).when(programRepository).findByIdAndUser(1L, userCallum);

        Program program = programService.getProgram(1L, CALLUM).get();
        assertNotNull(program);
        assertEquals(CALLUM, program.getUser().getUsername());
        assertEquals(PROGRAM_CALLUM, program.getName());
    }

    @Test
    void GetProgramWithWrongUsername() {
        doReturn(Optional.empty()).when(userService).getUser(CALLUM + "badname");

        Optional<Program> program = programService.getProgram(1L, CALLUM + "badname");
        assert(program.isEmpty());
    }

    @Test
    void addProgram() {
        Program programWithoutId = new Program(
                null,
                programCallum.getName(),
                programCallum.getProgramSetting(),
                programCallum.getFrequency(),
                programCallum.getWeeklyRoutines(),
                programCallum.getFrequencyRoutines(),
                programCallum.getIndividualRoutines(),
                programCallum.getUser()
        );

        doReturn(programCallum).when(programRepository).save(programWithoutId);

        Program program = programService.addProgram(programWithoutId, CALLUM).get();

        assertNotNull(program);
        assertEquals(CALLUM, program.getUser().getUsername());
        assertEquals(PROGRAM_CALLUM, program.getName());
    }

    @Test
    void editProgram() {
        doReturn(Optional.of(programCallum)).when(programRepository).findById(1L);
        doReturn(programCallum).when(programRepository).save(programCallum);

        programService.editProgram(programCallum, CALLUM);

        verify(programRepository).findById(1L);
        verify(programRepository).save(programCallum);
    }

    @Test
    void deleteProgram() {
        doReturn(Optional.of(programCallum)).when(programRepository).findById(1L);

        programService.deleteProgram(1L, CALLUM);

        verify(programRepository).findById(1L);
        verify(programRepository).deleteById(1L);
    }
}