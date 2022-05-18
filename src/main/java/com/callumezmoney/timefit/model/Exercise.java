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
    @ManyToMany(cascade = {
                    //CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @ToString.Exclude
    private List<Routine> routines;

    public void remove(){
        routines.forEach(routine -> routine.getExercises().remove(this));
        routines = new ArrayList();
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", name='" + name + '\'' +
                ", exerciseDuration=" + exerciseDuration +
                ", exerciseBreak=" + exerciseBreak +
                ", repetitions=" + repetitions +
                ", exerciseColor=" + exerciseColor +
                ", breakColor=" + breakColor +
                ", illustrationLocation='" + illustrationLocation + '\'' +
                ", exerciseSoundLocation='" + exerciseSoundLocation + '\'' +
                ", breakSoundLocation='" + breakSoundLocation + '\'' +
                ", countdownSoundLocation='" + countdownSoundLocation + '\'' +
                '}';
    }
}
