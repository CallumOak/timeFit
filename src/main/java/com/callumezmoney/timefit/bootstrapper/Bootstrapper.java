package com.callumezmoney.timefit.bootstrapper;

import com.callumezmoney.timefit.controller.AuthController;
import com.callumezmoney.timefit.model.*;
import com.callumezmoney.timefit.payload.request.SignupRequest;
import com.callumezmoney.timefit.repository.*;
import com.callumezmoney.timefit.util.ProgramSetting;
import com.callumezmoney.timefit.util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class Bootstrapper implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ExercisesRepository exercisesRepository;
    private final RoutineRepository routineRepository;
    private final AuthController authController;

    @Override
    @Transactional
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
        List<Exercise> exercises = createExercises(false);

        exercises.stream().forEach(e -> e.setUser(callum));
        exercisesRepository.saveAll(exercises);
        exercises = exercisesRepository.findAll();

        //create test routines
        List<Routine> routines = createRoutines(exercises,false);

        routines.stream().forEach(r -> r.setUser(callum));
        routineRepository.saveAll(routines);
        routines = routineRepository.findAll();

        //create test routine plans
        List<WeeklyRoutinePlan> weeklyRoutinePlans = createWeeklyRoutinePlans(routines, false);
        List<FrequencyRoutinePlan> frequencyRoutinePlans = createFrequencyRoutinePlans(routines, false);
        List<IndividualRoutinePlan> individualRoutinePlans = createIndividualRoutinePlans(routines, false);

        //test programs
        final List<Program> programs = createPrograms(weeklyRoutinePlans, frequencyRoutinePlans, individualRoutinePlans, false);

        programs.stream().forEach(program -> program.setUser(callum));
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
        //exercises.forEach(e -> e.setRoutines(new ArrayList<>()));

        Routine legs = new Routine(setIds ? 1L : null, null, "Legs", 2, Color.BLUE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        legs.setExercises(new ArrayList<>(Arrays.asList(exercises.get(3), exercises.get(4),exercises.get(5))), Arrays.asList(0,1,2));
        routines.add(legs);

        Routine arms = new Routine(setIds ? 2L : null, null, "Arms", 2, Color.BLUE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        arms.setExercises(new ArrayList<>(Arrays.asList(exercises.get(0), exercises.get(1),exercises.get(2))), Arrays.asList(0,1,2));
        routines.add(arms);

        Routine core = new Routine(setIds ? 3L : null, null, "Core", 2, Color.BLUE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        core.setExercises(new ArrayList<>(Arrays.asList(exercises.get(0), exercises.get(1),exercises.get(2))), Arrays.asList(0,1,2));
        routines.add(core);

        return routines;
    }

    public static List<WeeklyRoutinePlan> createWeeklyRoutinePlans(List<Routine> routines, Boolean setIds) throws ParseException {
        List<WeeklyRoutinePlan> routinePlans = new ArrayList<>();
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

        return routinePlans;
    }

    public static List<FrequencyRoutinePlan> createFrequencyRoutinePlans(List<Routine> routines, Boolean setIds) throws ParseException {
        List<FrequencyRoutinePlan> routinePlans = new ArrayList<>();
        FrequencyRoutinePlan armsFrequency = FrequencyRoutinePlan.builder()
                .id(setIds ? 4L : null).routine(routines.get(1)).startTime(LocalTime.of(15, 0)).position(0).build();
        routinePlans.add(armsFrequency);
        //arms.getRoutinePlans().add(armsFrequency);
        FrequencyRoutinePlan legsFrequency = FrequencyRoutinePlan.builder()
                .id(setIds ? 5L : null).routine(routines.get(0)).startTime(LocalTime.of(15, 0)).position(1).build();
        routinePlans.add(legsFrequency);
        //legs.getRoutinePlans().add(legsFrequency);
        FrequencyRoutinePlan coreFrequency = FrequencyRoutinePlan.builder()
                .id(setIds ? 6L : null).routine(routines.get(2)).startTime(LocalTime.of(15, 0)).position(2).build();
        routinePlans.add(coreFrequency);
        //core.getRoutinePlans().add(coreFrequency);

        return routinePlans;
    }

    public static List<IndividualRoutinePlan> createIndividualRoutinePlans(List<Routine> routines, Boolean setIds) throws ParseException {
        List<IndividualRoutinePlan> routinePlans = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        IndividualRoutinePlan individual = IndividualRoutinePlan.builder()
                .id(setIds ? 7L : null).date(format.parse("29/03/2021")).routine(routines.get(1)).startTime(LocalTime.of(15,0)).build();
        routinePlans.add(individual);

        return routinePlans;
    }

    public static List<Program> createPrograms(List<WeeklyRoutinePlan> weeklyRoutinePlans, List<FrequencyRoutinePlan> frequencyRoutinePlans, List<IndividualRoutinePlan> individualRoutinePlans, Boolean setIds){
        List<Program> programs = new ArrayList<>();
        Program callumProgram = Program.builder()
                .id(setIds ? 1L : null)
                .name("Callum's program").frequency(2)
                .weeklyRoutines(new ArrayList<>())
                .frequencyRoutines(new ArrayList<>())
                .individualRoutines(new ArrayList<>())
                .programSetting(ProgramSetting.WEEKLY)
                .startDate(new Date())
                .build();
        programs.add(callumProgram);
        weeklyRoutinePlans.forEach(r -> r.setProgram(callumProgram));
        frequencyRoutinePlans.forEach(r -> r.setProgram(callumProgram));
        individualRoutinePlans.forEach(r -> r.setProgram(callumProgram));
        return programs;
    }
}