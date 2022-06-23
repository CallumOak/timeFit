package com.callumezmoney.timefit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Program> programs = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Routine> routines = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setPrograms(List<Program> programs) {
        this.programs.forEach(p -> {if(!programs.contains(p))p.setUser(null);});
        programs.forEach(p->p.setUser(this));
    }

    public void setRoutines(List<Routine> routines) {
        this.routines.forEach(p -> {if(!routines.contains(p))p.setUser(null);});
        routines.forEach(p->p.setUser(this));
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises.forEach(p -> {if(!exercises.contains(p))p.setUser(null);});
        exercises.forEach(p->p.setUser(this));
    }
}
