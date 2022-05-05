package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.ProgramDTO;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.service.ProgramService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class ProgramMapper implements WebMapper<Program, ProgramDTO> {

    private Environment environment;
    private RoutinePlanMapper routinePlanMapper;
    private WeeklyRoutinePlanMapper weeklyRoutinePlanMapper;
    private FrequencyRoutinePlanMapper frequencyRoutinePlanMapper;
    private IndividualRoutinePlanMapper individualRoutinePlanMapper;
    private UserMapper userMapper;
    private ProgramService programService;

    @Override
    public Program dtoToEntity(ProgramDTO dto) {
        return new Program(
                dto.getId(),
                dto.getName(),
                dto.getProgramSetting(),
                dto.getFrequency(),
                dto.getWeeklyRoutines().stream().map(weeklyRoutinePlanMapper::dtoToEntity)
                        .collect(Collectors.toList()),
                dto.getFrequencyRoutines().stream().map(frequencyRoutinePlanMapper::dtoToEntity)
                        .collect(Collectors.toList()),
                dto.getIndividualRoutines().stream().map(individualRoutinePlanMapper::dtoToEntity)
                        .collect(Collectors.toList()),
                null
                );
    }

    @Override
    public ProgramDTO entityToDto(Program entity) {
        return new ProgramDTO(
                entity.getId(),
                entity.getName(),
                entity.getProgramSetting(),
                entity.getFrequency(),
                entity.getWeeklyRoutines().stream().map(weeklyRoutinePlanMapper::entityToDto)
                        .collect(Collectors.toList()),
                entity.getFrequencyRoutines().stream().map(frequencyRoutinePlanMapper::entityToDto)
                        .collect(Collectors.toList()),
                entity.getIndividualRoutines().stream().map(individualRoutinePlanMapper::entityToDto)
                        .collect(Collectors.toList()),
                userMapper.entityToDto(entity.getUser())
        );
    }

    @Override
    public String toURI(Program object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public Program fromURI(String uri) {
        return programService.getProgram(getIdFromURI(uri, environment)).get();
    }
}
