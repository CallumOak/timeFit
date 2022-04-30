package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoutineRepository extends CrudRepository<Routine, Long> {
    Optional<Routine> findByIdAndUser(Long id, User user);
}
