package com.callumezmoney.timefit.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDTO {
    String username;
    String email;
    String password;
    List<String> program;
    List<String> routines;
    List<String> exercises;
    RoleDTO role;
}
