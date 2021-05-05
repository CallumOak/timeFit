package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.util.RoleEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
