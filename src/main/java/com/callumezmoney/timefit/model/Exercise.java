package com.callumezmoney.timefit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String name;
    private Duration exerciseDuration;
    private Duration exerciseBreak;
    private Integer repetitions;
    private Color exerciseColor;
    private Color breakColor;
    private String illustrationLocation;
    private String exerciseSoundLocation;
    private String breakSoundLocation;
    private String countdownSoundLocation;
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ExerciseRoutine> routines;

    public void setUser(User user) {
        if(user == this.user){
            return;
        }
        if(this.user != null){
            this.user.getExercises().remove(this);
        }
        if(!user.getExercises().contains(this)){
            user.getExercises().add(this);
        }
        this.user = user;
    }

    // For explanations see setExercises in Routine
    public void setRoutines(List<Routine> routines, List<Integer> positions) {
        List<Routine> oldRoutines = new ArrayList<>();
        this.routines.forEach(r -> oldRoutines.add(r.getRoutine()));

        if(routines.equals(oldRoutines)){
            return;
        }
        ArrayList<Routine> tmpNewRoutines = new ArrayList<>(routines);
        ArrayList<ExerciseRoutine> tmpOldExerciseRoutines = new ArrayList<>(this.routines);
        tmpOldExerciseRoutines.forEach(r -> {
            if(!routines.contains(r.getRoutine())) {
                this.routines.remove(r);
                r.getRoutine().getExercises().remove(r);
                r.setExercise(null);
                r.setRoutine(null);
            }
            else tmpNewRoutines.remove(r.getRoutine());
        });
        for(int i = 0; i < routines.size(); i++){
            ExerciseRoutine er = new ExerciseRoutine();
            er.setExercise(this);
            er.setRoutine(routines.get(i));
            er.setPosition(positions.get(i));
            routines.get(i).getExercises().add(er);
            this.getRoutines().add(er);
        }
        tmpNewRoutines.forEach(r -> {
        });
    }
}
