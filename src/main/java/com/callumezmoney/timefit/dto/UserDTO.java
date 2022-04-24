package com.callumezmoney.timefit.dto;

import lombok.Value;

@Value
public class UserDTO {
    private String username;
    private String email;
    private ProgramDTO program;
    private RoleDTO role;
}
