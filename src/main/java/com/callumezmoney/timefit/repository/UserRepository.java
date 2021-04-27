package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
