package com.callumezmoney.timefit.dto;

import lombok.Value;

import java.util.List;

@Value
public class UserDTO {
    String username;
    String email;
    List<ProgramDTO> program;
    RoleDTO role;
}
