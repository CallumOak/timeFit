package com.callumezmoney.timefit.model;

import com.callumezmoney.timefit.dto.UserCreationDTO;
import com.callumezmoney.timefit.dto.UserDTO;
import com.callumezmoney.timefit.repository.UserRepository;
import com.callumezmoney.timefit.service.ProgramService;
import com.callumezmoney.timefit.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @OneToOne
//    @JoinColumn(name="program_id")
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
