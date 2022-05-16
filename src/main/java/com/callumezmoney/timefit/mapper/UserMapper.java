package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.UserCreationDTO;
import com.callumezmoney.timefit.dto.UserDTO;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
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
                dto.getProgram().stream().map(programMapper::fromURI).collect(Collectors.toList()),
                dto.getRoutines().stream().map(routineMapper::fromURI).collect(Collectors.toList()),
                dto.getExercises().stream().map(exerciseMapper::fromURI).collect(Collectors.toList()),
                roleMapper.dtoToEntity(dto.getRole()));
        user.getPrograms().forEach(p -> p.setUser(user));
        user.getRoutines().forEach(p -> p.setUser(user));
        user.getExercises().forEach(p -> p.setUser(user));
        return  user;
    }

    public User creationDtoToEntity(UserCreationDTO dto) {
        User user = new User(null,
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getProgram().stream().map(programMapper::fromURI).collect(Collectors.toList()),
                dto.getRoutines().stream().map(routineMapper::fromURI).collect(Collectors.toList()),
                dto.getExercises().stream().map(exerciseMapper::fromURI).collect(Collectors.toList()),
                roleMapper.dtoToEntity(dto.getRole()));
        user.getPrograms().forEach(p -> p.setUser(user));
        user.getRoutines().forEach(p -> p.setUser(user));
        user.getExercises().forEach(p -> p.setUser(user));
        return  user;
    }

    @Override
    public UserDTO entityToDto(User entity) {
        UserDTO user = new UserDTO(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPrograms().stream().map(p -> ProgramMapper.toURI(p, environment)).collect(Collectors.toList()),
                entity.getRoutines().stream().map(r -> RoutineMapper.toURI(r, environment)).collect(Collectors.toList()),
                entity.getExercises().stream().map(e -> ExerciseMapper.toURI(e, environment)).collect(Collectors.toList()),
                roleMapper.entityToDto(entity.getRole()));
        return user;
    }

    public UserCreationDTO entityToCreationDto(User entity) {
        return new UserCreationDTO(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPrograms().stream().map(p -> ProgramMapper.toURI(p, environment)).collect(Collectors.toList()),
                entity.getRoutines().stream().map(r -> RoutineMapper.toURI(r, environment)).collect(Collectors.toList()),
                entity.getExercises().stream().map(e -> ExerciseMapper.toURI(e, environment)).collect(Collectors.toList()),
                roleMapper.entityToDto(entity.getRole()));
    }

    public static  String toURI(User object, Environment environment) {
        return object != null ? environment.getProperty("callumezmoney.app.webapiprefix.user") + "/" + object.getId() : "";
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
