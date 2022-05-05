package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    String username;
    String email;
    List<ProgramDTO> program;
    List<RoutineDTO> routines;
    List<ExerciseDTO> exercises;
    RoleDTO role;
}
