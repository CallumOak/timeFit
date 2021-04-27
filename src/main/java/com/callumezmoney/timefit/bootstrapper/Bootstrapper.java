package com.callumezmoney.timefit.bootstrapper;

import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Bootstrapper implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        String usernames[] = {"John", "Julie", "Jennifer", "Helen", "Rachel"};
        for(String username: usernames){
            User user = new User();
            user.setFirstName(username);
            user.setEmail(username.toLowerCase() + "@domain.com");
            this.userRepository.save(user);
            System.out.println(user);
        }

    }
}
