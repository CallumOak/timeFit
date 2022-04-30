package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProgramRepository extends CrudRepository<Program, Long> {
    Optional<Program> findByIdAndUser(Long id, User user);
}
