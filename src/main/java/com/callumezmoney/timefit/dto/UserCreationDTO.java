package com.callumezmoney.timefit.dto;
import lombok.Value;

@Value
public class UserCreationDTO {
    private String username;
    private String email;
    private String password;
    private ProgramDTO program;
    private RoleDTO role;
}
