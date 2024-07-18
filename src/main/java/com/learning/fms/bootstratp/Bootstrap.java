package com.learning.fms.bootstratp;

import com.learning.fms.entity.RoleType;
import com.learning.fms.entity.User;
import com.learning.fms.entity.UserRole;
import com.learning.fms.repository.UserRepository;
import com.learning.fms.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() < 2) {
            var user1 = userRepository.save(User.builder()
                    .firstName("venky")
                    .lastName("repalle")
                    .email("rvkrishna13052001@gmail.com")
                    .password(passwordEncoder.encode("venky123"))
                    .mobileNumber("9059712824")
                    .build());
            userRoleRepository.save(UserRole.builder()
                    .user(user1)
                    .role(RoleType.CUSTOMER)
                    .build());

            var user2 = userRepository.save(User.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .mobileNumber("0123456789")
                    .build());
            userRoleRepository.save(UserRole.builder()
                    .user(user2)
                    .role(RoleType.ADMIN)
                    .build());
        }
    }
}
