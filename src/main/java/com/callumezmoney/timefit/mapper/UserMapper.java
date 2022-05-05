package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.UserCreationDTO;
import com.callumezmoney.timefit.dto.UserDTO;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.service.UserService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class UserMapper implements WebMapper<User, UserDTO>{

    private Environment environment;
    private ProgramMapper programMapper;
    private RoutineMapper routineMapper;
    private ExerciseMapper exerciseMapper;
    private RoleMapper roleMapper;
    private UserService userService;

    @Override
    public User dtoToEntity(UserDTO dto) {
        User user = new User(getUserId(dto),
                dto.getUsername(),
                dto.getEmail(),
                getUserPassword(dto),
                dto.getProgram().stream().map(programMapper::dtoToEntity).collect(Collectors.toList()),
                dto.getRoutines().stream().map(routineMapper::dtoToEntity).collect(Collectors.toList()),
                dto.getExercises().stream().map(exerciseMapper::dtoToEntity).collect(Collectors.toList()),
                roleMapper.dtoToEntity(dto.getRole()));
        user.getPrograms().stream().forEach(p -> p.setUser(user));
        user.getRoutines().stream().forEach(p -> p.setUser(user));
        user.getExercises().stream().forEach(p -> p.setUser(user));
        return  user;
    }

    @Override
    public UserDTO entityToDto(User entity) {
        UserDTO user = new UserDTO(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPrograms().stream().map(programMapper::entityToDto).collect(Collectors.toList()),
                entity.getRoutines().stream().map(routineMapper::entityToDto).collect(Collectors.toList()),
                entity.getExercises().stream().map(exerciseMapper::entityToDto).collect(Collectors.toList()),
                roleMapper.entityToDto(entity.getRole()));
        user.getProgram().stream().forEach(p -> p.setUser(user));
        user.getRoutines().stream().forEach(p -> p.setUser(user));
        user.getExercises().stream().forEach(p -> p.setUser(user));
        return user;
    }

    public UserCreationDTO entityToCreationDto(User entity) {
        return new UserCreationDTO(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPrograms().stream().map(programMapper::entityToDto).collect(Collectors.toList()),
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
