package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ExercisesRepository extends CrudRepository<Exercise, Long> {
    Set<Exercise> findAll();

    Optional<Exercise> findByIdAndUser(Long id, User user);
}
