package com.callumezmoney.timefit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @OneToMany(mappedBy = "routine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoutinePlan> routinePlans;

    public void remove(){
        exercises.forEach(exercise -> exercise.getRoutines().remove(this));
        exercises = new ArrayList();
    }
    public void setExercises(List<Exercise> exercises){
        exercises.forEach(e -> {
            if(e.getRoutines().stream().noneMatch(r -> Objects.equals(r.getId(), this.getId())))
                e.getRoutines().add(this);
        });
        this.exercises = exercises;
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
