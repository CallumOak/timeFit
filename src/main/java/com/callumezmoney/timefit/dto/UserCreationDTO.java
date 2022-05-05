package com.callumezmoney.timefit.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class UserCreationDTO {
    String username;
    String email;
    String password;
    List<ProgramDTO> program;
    RoleDTO role;
}
