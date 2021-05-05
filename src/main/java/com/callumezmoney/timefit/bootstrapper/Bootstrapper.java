package com.callumezmoney.timefit.bootstrapper;

import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.RoleRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import com.callumezmoney.timefit.util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class Bootstrapper implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        String usernames[] = {"John", "Julie", "Jennifer", "Helen", "Rachel"};
        for(String username: usernames){
            User user = new User();
            user.setEmail(username.toLowerCase() + "@domain.com");
            user.setPassword(username+"123");
            user.setUsername(username);
            this.userRepository.save(user);
            System.out.println(user);
        }

        Role user = new Role();
        Role moderator = new Role();
        Role admin = new Role();
        user.setName(RoleEnum.ROLE_USER);
        moderator.setName(RoleEnum.ROLE_MODERATOR);
        admin.setName(RoleEnum.ROLE_ADMIN);
        roleRepository.saveAll(Arrays.asList(user, moderator, admin));


    }
}