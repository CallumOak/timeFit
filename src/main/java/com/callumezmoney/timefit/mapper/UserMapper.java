package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.UserCreationDTO;
import com.callumezmoney.timefit.dto.UserDTO;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.service.UserService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class UserMapper implements WebMapper<User, UserDTO>{

    private Environment environment;
    private ProgramMapper programMapper;
    private RoleMapper roleMapper;
    private UserService userService;

    @Override
    public User dtoToEntity(UserDTO dto) {
        return new User(getUserId(dto),
                dto.getUsername(),
                dto.getEmail(),
                getUserPassword(dto),
                programMapper.dtoToEntity(dto.getProgram()),
                roleMapper.dtoToEntity(dto.getRole()));
    }

    @Override
    public UserDTO entityToDto(User entity) {
        return new UserDTO(
                entity.getUsername(),
                entity.getEmail(),
                programMapper.entityToDto(entity.getProgram()),
                roleMapper.entityToDto(entity.getRole()));
    }

    public UserCreationDTO entityToCreationDto(User entity) {
        return new UserCreationDTO(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                programMapper.entityToDto(entity.getProgram()),
                roleMapper.entityToDto(entity.getRole()));
    }

    @Override
    public String toURI(User object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public User fromURI(String uri) {
        return userService.getUser(getIdFromURI(uri, environment)).get();
    }

    private boolean userExists(UserDTO userDto) {
        return userService.getUser(userDto.getUsername()).isPresent();
    }

    private Long getUserId(UserDTO userDto){
        return userExists(userDto) ? userService.getUser(userDto.getUsername()).get().getId() : null;
    }

    private String getUserPassword(UserDTO userDto){
        return userExists(userDto) ? userService.getUser(userDto.getUsername()).get().getPassword() : "";
    }
}
