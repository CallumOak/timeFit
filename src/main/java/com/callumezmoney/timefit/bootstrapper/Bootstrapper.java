package com.callumezmoney.timefit.bootstrapper;

import com.callumezmoney.timefit.controller.AuthController;
import com.callumezmoney.timefit.model.*;
import com.callumezmoney.timefit.payload.request.SignupRequest;
import com.callumezmoney.timefit.repository.*;
import com.callumezmoney.timefit.util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class Bootstrapper implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ExercisesRepository exercisesRepository;
    private final RoutineRepository routineRepository;
    private final RoutinePlanRepository routinePlanRepository;
    private final ProgramRepository programRepository;
    private final AuthController authController;

    @Override
    public void run(String... args) throws Exception {

        //test users
        Role user = new Role();
        Role moderator = new Role();
        Role admin = new Role();
        user.setName(RoleEnum.ROLE_USER);
        moderator.setName(RoleEnum.ROLE_MODERATOR);
        admin.setName(RoleEnum.ROLE_ADMIN);
        roleRepository.saveAll(Arrays.asList(user, moderator, admin));

        String[] usernames = {"John", "Julie", "Jennifer", "Helen", "Rachel", "Callum"};
        for(String username: usernames){
            SignupRequest newUserRequest = new SignupRequest();
            newUserRequest.setEmail(username.toLowerCase() + "@domain.com");
            newUserRequest.setPassword(username+"123");
            newUserRequest.setUsername(username);
            authController.registerUser(newUserRequest);
        }

        User callum = userRepository.findByUsername("Callum").get();
        //create test exercises
        List<Exercise> exercises = createExercises(false);

        exercisesRepository.saveAll(exercises);
        exercises.stream().forEach(e -> e.setUser(callum));
        exercisesRepository.saveAll(exercises);

        //create test routines
        List<Routine> routines = createRoutines(exercises,false);

        routineRepository.saveAll(routines);
        routines.stream().forEach(r -> r.setUser(callum));

        //create test routine plans
        List<RoutinePlan> routinePlans = createRoutinePlans(routines, false);

        routinePlanRepository.saveAll(routinePlans);

        //test programs
        List<Program> programs = createPrograms(routinePlans, false);

        programRepository.saveAll(programs);
        programs.stream().forEach(program -> program.setUser(callum));
        programRepository.saveAll(programs);

        exercisesRepository.saveAll(exercises);
        routineRepository.saveAll(routines);
        routinePlanRepository.saveAll(routinePlans);
        programRepository.saveAll(programs);
    }

    public static List<Exercise> createExercises(Boolean setIds){
        List<Exercise> exercises = new ArrayList<>();

        Exercise bicepCurl = new Exercise(
                setIds ? 1L : null, null, "Bicep curl", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(bicepCurl);
        Exercise pushups = new Exercise(
                setIds ? 2L : null, null, "Pushups", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(pushups);
        Exercise pullups = new Exercise(
                setIds ? 3L : null, null, "Pullups", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(pullups);
        Exercise squats = new Exercise(
                setIds ? 4L : null, null, "Squats", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(squats);
        Exercise lunges = new Exercise(
                setIds ? 5L : null, null, "Lunges", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(lunges);
        Exercise sideRaise = new Exercise(
                setIds ? 6L : null, null, "Side raises", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(sideRaise);
        Exercise crunches = new Exercise(
                setIds ? 7L : null, null, "Crunches", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(crunches);
        Exercise sideCrunch = new Exercise(
                setIds ? 8L : null, null, "Side crunches", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(sideCrunch);
        Exercise plank = new Exercise(
                setIds ? 9L : null, null, "Plank", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        exercises.add(plank);

        return exercises;
    }

    public static List<Routine> createRoutines(List<Exercise> exercises, Boolean setIds){
        List<Routine> routines = new ArrayList<>();

        Routine legs = new Routine(setIds ? 1L : null, null, "Legs", 2, Color.BLUE, Arrays.asList(exercises.get(3), exercises.get(4), exercises.get(5)), Arrays.asList());
        exercises.get(3).getRoutines().add(legs);
        exercises.get(4).getRoutines().add(legs);
        exercises.get(5).getRoutines().add(legs);
        routines.add(legs);

        Routine arms = new Routine(setIds ? 2L : null, null, "Arms", 2, Color.BLUE, Arrays.asList(exercises.get(0), exercises.get(1), exercises.get(2)), Arrays.asList());
        exercises.get(0).getRoutines().add(arms);
        exercises.get(1).getRoutines().add(arms);
        exercises.get(2).getRoutines().add(arms);
        routines.add(arms);

        Routine core = new Routine(setIds ? 3L : null, null, "Core", 2, Color.BLUE, Arrays.asList(exercises.get(6), exercises.get(7), exercises.get(8)), Arrays.asList());
        exercises.get(6).getRoutines().add(core);
        exercises.get(7).getRoutines().add(core);
        exercises.get(8).getRoutines().add(core);
        routines.add(core);

        return routines;
    }

    public static List<RoutinePlan> createRoutinePlans(List<Routine> routines, Boolean setIds) throws ParseException {
        List<RoutinePlan> routinePlans = new ArrayList<>();
        WeeklyRoutinePlan armsWeekly = WeeklyRoutinePlan.builder()
                .id(setIds ? 1L : null).weekDay(0).routine(routines.get(1)).startTime(LocalTime.of(15, 0)).build();
        routinePlans.add(armsWeekly);
        //arms.getRoutinePlans().add(armsWeekly);
        WeeklyRoutinePlan legsWeekly = WeeklyRoutinePlan.builder()
                .id(setIds ? 2L : null).weekDay(2).routine(routines.get(0)).startTime(LocalTime.of(15, 0)).build();
        routinePlans.add(legsWeekly);
        //legs.getRoutinePlans().add(legsWeekly);
        WeeklyRoutinePlan coreWeekly = WeeklyRoutinePlan.builder()
                .id(setIds ? 3L : null).weekDay(4).routine(routines.get(2)).startTime(LocalTime.of(15, 0)).build();
        routinePlans.add(coreWeekly);
        //core.getRoutinePlans().add(coreWeekly);
        FrequencyRoutinePlan armsFrequency = FrequencyRoutinePlan.builder()
                .id(setIds ? 4L : null).routine(routines.get(1)).startTime(LocalTime.of(15, 0)).build();
        routinePlans.add(armsFrequency);
        //arms.getRoutinePlans().add(armsFrequency);
        FrequencyRoutinePlan legsFrequency = FrequencyRoutinePlan.builder()
                .id(setIds ? 5L : null).routine(routines.get(0)).startTime(LocalTime.of(15, 0)).build();
        routinePlans.add(legsFrequency);
        //legs.getRoutinePlans().add(legsFrequency);
        FrequencyRoutinePlan coreFrequency = FrequencyRoutinePlan.builder()
                .id(setIds ? 6L : null).routine(routines.get(2)).startTime(LocalTime.of(15, 0)).build();
        routinePlans.add(coreFrequency);
        //core.getRoutinePlans().add(coreFrequency);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        IndividualRoutinePlan individual = IndividualRoutinePlan.builder()
                .id(setIds ? 7L : null).date(format.parse("29/03/2021")).routine(routines.get(1)).startTime(LocalTime.of(15,0)).build();
        routinePlans.add(individual);

        return routinePlans;
    }

    public static List<Program> createPrograms(List<RoutinePlan> routinePlans, Boolean setIds){
        List<Program> programs = new ArrayList<>();
        Program callumProgram = Program.builder()
                .id(setIds ? 1L : null)
                .name("Callum's program").frequency(2)
                .weeklyRoutines(Arrays.asList((WeeklyRoutinePlan) routinePlans.get(0), (WeeklyRoutinePlan) routinePlans.get(1), (WeeklyRoutinePlan) routinePlans.get(2)))
                .frequencyRoutines(Arrays.asList((FrequencyRoutinePlan) routinePlans.get(3), (FrequencyRoutinePlan) routinePlans.get(4), (FrequencyRoutinePlan) routinePlans.get(5)))
                .individualRoutines(Collections.singletonList((IndividualRoutinePlan) routinePlans.get(6)))
                .build();
        programs.add(callumProgram);
        return programs;
    }

    public static List<RoutinePlan> addRoutinePlansToPrograms(List<RoutinePlan> routinePlans, List<Program> programs){
        routinePlans.forEach(rp -> rp.setProgram(programs.get(0)));
        return routinePlans;
    }
}