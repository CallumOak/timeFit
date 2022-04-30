package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.RoutinePlan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoutinePlanRepository extends CrudRepository<RoutinePlan, Long> {
    List<RoutinePlan> findAll();
}
