package com.callumezmoney.timefit.bootstrapper;

import com.callumezmoney.timefit.controller.AuthController;
import com.callumezmoney.timefit.model.*;
import com.callumezmoney.timefit.payload.request.SignupRequest;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import com.callumezmoney.timefit.repository.RoleRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Component
@AllArgsConstructor
public class Bootstrapper implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ExercisesRepository exercisesRepository;
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
        //test exercises
        Exercise bicepCurl = new Exercise(
                null, null, "Bicep curl", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        Exercise pushups = new Exercise(
                null, null, "Pushups", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        Exercise pullups = new Exercise(
                null, null, "Pullups", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());

        Exercise squats = new Exercise(
                null, null, "Squats", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        Exercise lunges = new Exercise(
                null, null, "Lunges", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        Exercise sideRaise = new Exercise(
                null, null, "Side raises", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());

        Exercise crunches = new Exercise(
                null, null, "Crunches", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        Exercise sideCrunch = new Exercise(
                null, null, "Side crunches", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());
        Exercise plank = new Exercise(
                null, null, "Plank", Duration.ofSeconds(20), Duration.ofSeconds(10), 5,
                Color.RED, Color.GREEN, "", "", "",
                "", new ArrayList<>());

        //test routines
        Routine legs = new Routine(null, null, "Legs", 2, Color.BLUE, Arrays.asList(squats, lunges, sideRaise), Arrays.asList());
        squats.getRoutines().add(legs);
        lunges.getRoutines().add(legs);
        sideRaise.getRoutines().add(legs);

        Routine arms = new Routine(null, null, "Arms", 2, Color.BLUE, Arrays.asList(bicepCurl, pushups, pullups), Arrays.asList());
        bicepCurl.getRoutines().add(arms);
        pushups.getRoutines().add(arms);
        pullups.getRoutines().add(arms);

        Routine core = new Routine(null, null, "Core", 2, Color.BLUE, Arrays.asList(crunches, sideCrunch, plank), Arrays.asList());
        crunches.getRoutines().add(core);
        sideCrunch.getRoutines().add(core);
        plank.getRoutines().add(core);

        //test routine plans
        WeeklyRoutinePlan armsWeekly = WeeklyRoutinePlan.builder()
                .weekDay(0).routine(arms).startTime(LocalTime.of(15, 0)).build();
        //arms.getRoutinePlans().add(armsWeekly);
        WeeklyRoutinePlan legsWeekly = WeeklyRoutinePlan.builder()
                .weekDay(2).routine(legs).startTime(LocalTime.of(15, 0)).build();
        //legs.getRoutinePlans().add(legsWeekly);
        WeeklyRoutinePlan coreWeekly = WeeklyRoutinePlan.builder()
                .weekDay(4).routine(core).startTime(LocalTime.of(15, 0)).build();
        //core.getRoutinePlans().add(coreWeekly);
        FrequencyRoutinePlan armsFrequency = FrequencyRoutinePlan.builder()
                .routine(arms).startTime(LocalTime.of(15, 0)).build();
        //arms.getRoutinePlans().add(armsFrequency);
        FrequencyRoutinePlan legsFrequency = FrequencyRoutinePlan.builder()
                .routine(legs).startTime(LocalTime.of(15, 0)).build();
        //legs.getRoutinePlans().add(legsFrequency);
        FrequencyRoutinePlan coreFrequency = FrequencyRoutinePlan.builder()
                .routine(core).startTime(LocalTime.of(15, 0)).build();
        //core.getRoutinePlans().add(coreFrequency);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        IndividualRoutinePlan individual = IndividualRoutinePlan.builder()
                .date(format.parse("29/03/2021")).routine(arms).startTime(LocalTime.of(15,0)).build();
        //arms.getRoutinePlans().add(individual);

        //test programs
        Program callumProgram = Program.builder()
                .name("Callum's program").frequency(2)
                .weeklyRoutines(Arrays.asList(armsWeekly, legsWeekly, coreWeekly))
                .frequencyRoutines(Arrays.asList(armsFrequency, legsFrequency, coreFrequency))
                .individualRoutines(Collections.singletonList(individual))
                .build();
        armsWeekly.setProgram(callumProgram);
        legsWeekly.setProgram(callumProgram);
        coreWeekly.setProgram(callumProgram);
        armsFrequency.setProgram(callumProgram);
        legsFrequency.setProgram(callumProgram);
        coreFrequency.setProgram(callumProgram);
        individual.setProgram(callumProgram);
    }
}