package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.ExerciseDTO;
import com.callumezmoney.timefit.mapper.ExerciseMapper;
import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceImplTest {
    public static final String CALLUM = "callum";
    public static final String LELE = "lele";
    public static final String EXERCISE_1 = "Bicep curl";
    @Mock
    private ExercisesRepository exercisesRepository;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private ExerciseServiceImpl exerciseService;
    private User userCallum;
    private User userLele;
    private List<Exercise> exerciseList;
    private Exercise exercise1WithoutId;
    private ExerciseMapper exerciseMapper;

    @BeforeEach
    public void setUp(){
        Role role = new Role(1L, RoleEnum.ROLE_USER);
        userCallum = new User(1L, CALLUM,
                CALLUM + "@cal.lum",
                CALLUM + "123",
                null,null,null,
                role);
        userLele = new User(2L, LELE,
                LELE + "@le.le",
                LELE + "123",
                null,null,null,
                role);
        exerciseList = new ArrayList<>();
        exercise1WithoutId = new Exercise(
                null, userCallum, EXERCISE_1, Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                "#8d92dd", "#8f8f8f", "", "", "",
                "", new ArrayList<>());
        Exercise bicepCurl = new Exercise(
                1L, userCallum, EXERCISE_1, Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                "#8d92dd", "#8f8f8f", "", "", "",
                "", new ArrayList<>());
        exerciseList.add(bicepCurl);
        Exercise pushups = new Exercise(
                2L, userCallum, "Pushups", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                "#8d92dd", "#8f8f8f", "", "", "",
                "", new ArrayList<>());
        exerciseList.add(pushups);
        Exercise pullups = new Exercise(
                3L, userCallum, "Pullups", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                "#8d92dd", "#8f8f8f", "", "", "",
                "", new ArrayList<>());
        exerciseList.add(pullups);
        Exercise squats = new Exercise(
                4L, userLele, "Squats", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                "#8d92dd", "#8f8f8f", "", "", "",
                "", new ArrayList<>());
        exerciseList.add(squats);
        Exercise lunges = new Exercise(
                5L, userLele, "Lunges", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                "#8d92dd", "#8f8f8f", "", "", "",
                "", new ArrayList<>());
        exerciseList.add(lunges);
        Exercise sideRaise = new Exercise(
                6L, userLele, "Side raises", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                "#8d92dd", "#8f8f8f", "", "", "",
                "", new ArrayList<>());
        exerciseList.add(sideRaise);
    }

    @Test
    void getAllExercises() {
        doReturn(exerciseList).when(exercisesRepository).findAll();
        assert(exercisesRepository.findAllByUser(userCallum) != null);

        List<Exercise> exercises = exerciseService.getAllExercises(userCallum.getUsername());
        assertNotNull(exercises);
        assertEquals(3, exercises.size());
    }

    @Test
    void getExerciseWithUsername() {
        doReturn(Optional.of(userCallum)).when(userService).getUser(CALLUM);
        doReturn(Optional.of(exerciseList.get(0))).when(exercisesRepository).findByIdAndUser(1L, userCallum);

        Exercise exercise = exerciseService.getExercise(1L, CALLUM).get();
        assertNotNull(exercise);
        assertEquals(CALLUM, exercise.getUser().getUsername());
        assertEquals(EXERCISE_1, exercise.getName());
    }

    @Test
    void getExerciseWithWrongUsername() {
        doReturn(Optional.empty()).when(userService).getUser(CALLUM + "badname");

        Optional<Exercise> exercise = exerciseService.getExercise(1L, CALLUM + "badname");
        assert(exercise.isEmpty());
    }

    @Test
    void GetExerciseWithoutUsername() {
        doReturn(Optional.of(exerciseList.get(0))).when(exercisesRepository).findById(1L);

        Exercise exercise = exerciseService.getExercise(1L).get();
        assertNotNull(exercise);
        assertEquals(CALLUM, exercise.getUser().getUsername());
        assertEquals(EXERCISE_1, exercise.getName());
    }

    @Test
    void addExercise() {
        doReturn(exerciseList.get(0)).when(exercisesRepository).save(exercise1WithoutId);

        Exercise exercise = exerciseService.addExercise(exercise1WithoutId, CALLUM).get();

        assertNotNull(exercise);
        assertEquals(CALLUM, exercise.getUser().getUsername());
        assertEquals(EXERCISE_1, exercise.getName());
    }

    @Test
    void addExerciseWithWrongUsername() {
        Optional<Exercise> exercise = exerciseService.addExercise(exercise1WithoutId, CALLUM + "badname");
        assert(exercise.isEmpty());
    }

    @Test
    void editExercise() {
        doReturn(Optional.of(exerciseList.get(0))).when(exercisesRepository).findById(1L);
        doReturn(exerciseList.get(0)).when(exercisesRepository).save(exerciseList.get(0));

        exerciseService.editExercise(exerciseMapper.entityToDto(exerciseList.get(0)), CALLUM);

        verify(exercisesRepository).findById(1L);
        verify(exercisesRepository).save(exerciseList.get(0));
    }

    @Test
    void deleteExercise() {
        doReturn(Optional.of(exerciseList.get(0))).when(exercisesRepository).findById(1L);

        exerciseService.deleteExercise(1L, CALLUM);

        verify(exercisesRepository).findById(1L);
        verify(exercisesRepository).deleteById(1L);
    }
}