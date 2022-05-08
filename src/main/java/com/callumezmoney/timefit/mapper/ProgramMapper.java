package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.ProgramDTO;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.service.ProgramService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
public class ProgramMapper implements WebMapper<Program, ProgramDTO> {

    private Environment environment;
    private WeeklyRoutinePlanMapper weeklyRoutinePlanMapper;
    private FrequencyRoutinePlanMapper frequencyRoutinePlanMapper;
    private IndividualRoutinePlanMapper individualRoutinePlanMapper;
    private ProgramService programService;

    @Override
    public Program dtoToEntity(ProgramDTO dto) {
        Program program = new Program(
                dto.getId(),
                dto.getName(),
                dto.getProgramSetting(),
                dto.getFrequency(),
                dto.getWeeklyRoutines().stream().map(weeklyRoutinePlanMapper::fromURI)
                        .collect(Collectors.toList()),
                dto.getFrequencyRoutines().stream().map(frequencyRoutinePlanMapper::fromURI)
                        .collect(Collectors.toList()),
                dto.getIndividualRoutines().stream().map(individualRoutinePlanMapper::fromURI)
                        .collect(Collectors.toList()),
                null
        );

        program.getFrequencyRoutines().forEach(r -> r.setProgram(program));
        program.getWeeklyRoutines().forEach(r -> r.setProgram(program));
        program.getIndividualRoutines().forEach(r -> r.setProgram(program));

        return program;

    }

    @Override
    public ProgramDTO entityToDto(Program entity) {
        return new ProgramDTO(
                entity.getId(),
                entity.getName(),
                entity.getProgramSetting(),
                entity.getFrequency(),
                entity.getWeeklyRoutines().stream().map(r -> WeeklyRoutinePlanMapper.toURI(r, environment))
                        .collect(Collectors.toList()),
                entity.getFrequencyRoutines().stream().map(r -> FrequencyRoutinePlanMapper.toURI(r, environment))
                        .collect(Collectors.toList()),
                entity.getIndividualRoutines().stream().map(r -> IndividualRoutinePlanMapper.toURI(r, environment))
                        .collect(Collectors.toList()),
                UserMapper.toURI(entity.getUser(), environment)
        );
    }

    public static String toURI(Program object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + "/" + object.getId();
    }

    @Override
    public Program fromURI(String uri) {
        return programService.getProgram(getIdFromURI(uri, environment)).get();
    }
}
