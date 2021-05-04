package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany
    private List<Role> roles = new ArrayList<>();
    @OneToOne
//    @JoinColumn(name="program_id")
    private Program program;

}
