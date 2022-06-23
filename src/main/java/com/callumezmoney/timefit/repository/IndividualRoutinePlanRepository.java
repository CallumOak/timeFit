package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.IndividualRoutinePlan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IndividualRoutinePlanRepository extends CrudRepository<IndividualRoutinePlan, Long> {
    List<IndividualRoutinePlan> findAll();
}
