package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUser(String email, String password);
}
