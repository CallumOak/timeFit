package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.FrequencyRoutinePlan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FrequencyRoutinePlanRepository extends CrudRepository<FrequencyRoutinePlan, Long> {
    List<FrequencyRoutinePlan> findAll();
}
