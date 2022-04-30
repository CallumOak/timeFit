package com.callumezmoney.timefit.dto;
import lombok.Value;

import java.util.List;

@Value
public class UserCreationDTO {
    String username;
    String email;
    String password;
    List<ProgramDTO> program;
    RoleDTO role;
}
