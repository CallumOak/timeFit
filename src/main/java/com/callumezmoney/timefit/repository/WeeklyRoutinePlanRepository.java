package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.WeeklyRoutinePlan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeeklyRoutinePlanRepository extends CrudRepository<WeeklyRoutinePlan, Long> {
    List<WeeklyRoutinePlan> findAll();
}
