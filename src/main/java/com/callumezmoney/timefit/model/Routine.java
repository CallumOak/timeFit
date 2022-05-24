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
    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<ExerciseRoutine> exercises = new ArrayList<>();
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
        exercises.forEach(exercise -> exercise.getExercise().getRoutines().remove(exercise));
        exercises = new ArrayList();
    }

    /**
     * Since there can be multiple of the same exercise,
     * the join table must have a separate ID to allow multiple of the same relation and
     * the logic must track how many are to be removed or added
     * @param exercises
     */
    public void setExercises(List<Exercise> exercises, List<Integer> positions){
        //Check changes are needed
        List<Exercise> tmpOldExercises = new ArrayList<>();
        this.exercises.forEach(e -> tmpOldExercises.add(e.getExercise()));
        if(exercises.equals(tmpOldExercises)){
            return;
        }
        //create a copy of the new exercises to allow removing already treated ones without affecting the actual list
        List<Exercise> tmpNewExercises = new ArrayList<>(exercises);

        //Remove this from the exercises that are not in the new list,
        //or remove the exercise from new exercises copy to track that it's been counted
        this.exercises.forEach(e -> {
            if(!tmpNewExercises.contains(e.getExercise()))e.getExercise().getRoutines().remove(e);
            else tmpNewExercises.remove(e.getExercise());
        });
        //Add this to the remaining exercises in new exercises copy and the remaining exercises to this
        for(int i = 0; i < exercises.size(); i++){
            ExerciseRoutine er = new ExerciseRoutine();
            er.setRoutine(this);
            er.setExercise(exercises.get(i));
            er.setPosition(positions.get(i));
            exercises.get(i).getRoutines().add(er);
            this.getExercises().add(er);
        }
    }

    public void setWeeklyRoutinePlans(List<WeeklyRoutinePlan> weeklyRoutinePlans) {
        if(weeklyRoutinePlans == this.weeklyRoutinePlans){
            return;
        }
        this.weeklyRoutinePlans.forEach(r -> {if(!weeklyRoutinePlans.contains(r))r.setRoutine(null);});
        weeklyRoutinePlans.forEach(r -> {if(!this.weeklyRoutinePlans.contains(r))r.setRoutine(this);});
        this.weeklyRoutinePlans = weeklyRoutinePlans;
    }

    public void setFrequencyRoutinePlans(List<FrequencyRoutinePlan> frequencyRoutinePlans) {
        if(frequencyRoutinePlans == this.frequencyRoutinePlans){
            return;
        }
        this.frequencyRoutinePlans.forEach(r -> {if(!frequencyRoutinePlans.contains(r))r.setRoutine(null);});
        frequencyRoutinePlans.forEach(r -> {if(!this.frequencyRoutinePlans.contains(r))r.setRoutine(this);});
        this.frequencyRoutinePlans = frequencyRoutinePlans;
    }

    public void setIndividualRoutinePlans(List<IndividualRoutinePlan> individualRoutinePlans) {
        if(individualRoutinePlans == this.individualRoutinePlans){
            return;
        }
        this.individualRoutinePlans.forEach(r -> {if(!individualRoutinePlans.contains(r))r.setRoutine(null);});
        individualRoutinePlans.forEach(r -> {if(!this.individualRoutinePlans.contains(r))r.setRoutine(this);});
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
