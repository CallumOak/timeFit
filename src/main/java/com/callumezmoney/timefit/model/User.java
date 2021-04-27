package com.callumezmoney.timefit.model;

import com.callumezmoney.timefit.util.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role = Role.CUSTOMER;
    @OneToOne
//    @JoinColumn(name="program_id")
    private Program program;

}
