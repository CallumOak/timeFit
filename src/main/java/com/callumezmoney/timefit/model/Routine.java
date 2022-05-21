package com.callumezmoney.timefit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String name;
    private Integer numberOfCycles;
    private Color color;
    @ManyToMany(mappedBy = "routines")
    private List<Exercise> exercises;
    @OneToMany(
            mappedBy = "routine",
            //fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<WeeklyRoutinePlan> weeklyRoutinePlans = new ArrayList<>();
    @OneToMany(
            mappedBy = "routine",
            //fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FrequencyRoutinePlan> frequencyRoutinePlans = new ArrayList<>();
    @OneToMany(
            mappedBy = "routine",
            //fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IndividualRoutinePlan> individualRoutinePlans = new ArrayList<>();

    public void remove(){
        exercises.forEach(exercise -> exercise.getRoutines().remove(this));
        exercises = new ArrayList();
    }
    public void setExercises(List<Exercise> exercises){
        if(exercises == this.exercises){
            return;
        }
        this.exercises.forEach(r -> {if(!exercises.contains(r))r.getRoutines().remove(r);});
        exercises.forEach(r -> {if(exercises.contains(r))r.getRoutines().add(this);});
        this.exercises = exercises;
    }

    public void setWeeklyRoutinePlans(List<WeeklyRoutinePlan> weeklyRoutinePlans) {
        if(weeklyRoutinePlans == this.weeklyRoutinePlans){
            return;
        }
        this.weeklyRoutinePlans.forEach(r -> {if(!weeklyRoutinePlans.contains(r))r.setRoutine(null);});
        weeklyRoutinePlans.forEach(r -> {if(weeklyRoutinePlans.contains(r))r.setRoutine(this);});
        this.weeklyRoutinePlans = weeklyRoutinePlans;
    }

    public void setFrequencyRoutinePlans(List<FrequencyRoutinePlan> frequencyRoutinePlans) {
        if(frequencyRoutinePlans == this.frequencyRoutinePlans){
            return;
        }
        this.frequencyRoutinePlans.forEach(r -> {if(!frequencyRoutinePlans.contains(r))r.setRoutine(null);});
        frequencyRoutinePlans.forEach(r -> {if(frequencyRoutinePlans.contains(r))r.setRoutine(this);});
        this.frequencyRoutinePlans = frequencyRoutinePlans;
    }

    public void setIndividualRoutinePlans(List<IndividualRoutinePlan> individualRoutinePlans) {
        if(individualRoutinePlans == this.individualRoutinePlans){
            return;
        }
        this.individualRoutinePlans.forEach(r -> {if(!individualRoutinePlans.contains(r))r.setRoutine(null);});
        individualRoutinePlans.forEach(r -> {if(individualRoutinePlans.contains(r))r.setRoutine(this);});
        this.individualRoutinePlans = individualRoutinePlans;
    }

    public void setUser(User user) {
        if(user == this.user){
            return;
        }
        if(this.user != null){
            this.user.getRoutines().remove(this);
        }
        if(!user.getRoutines().contains(this)){
            user.getRoutines().add(this);
        }
        this.user = user;
    }

    @Override
    public String toString() {
        return "Routine{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", name='" + name + '\'' +
                ", numberOfCycles=" + numberOfCycles +
                ", color=" + color +
                '}';
    }
}
