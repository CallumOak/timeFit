package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.ProgramDTO;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.service.ProgramService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;
import static com.callumezmoney.timefit.util.ProgramSetting.fromValue;

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
    public Program dtoToEntity(ProgramDTO dto) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Program program = new Program(
                dto.getId(),
                dto.getName(),
                fromValue(dto.getProgramSetting()),
                dto.getFrequency(),
                formatter.parse(dto.getStartDate()),
                dto.getWeeklyRoutinePlans().stream().map(weeklyRoutinePlanMapper::fromURI)
                        .collect(Collectors.toList()),
                dto.getFrequencyRoutinePlans().stream().map(frequencyRoutinePlanMapper::fromURI)
                        .collect(Collectors.toList()),
                dto.getIndividualRoutinePlans().stream().map(individualRoutinePlanMapper::fromURI)
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
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new ProgramDTO(
                entity.getId(),
                entity.getName(),
                entity.getProgramSetting().value(),
                entity.getFrequency(),
                formatter.format(entity.getStartDate()),
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
        return programService.getProgram(getIdFromURI(uri, environment, "program")).get();
    }
}
