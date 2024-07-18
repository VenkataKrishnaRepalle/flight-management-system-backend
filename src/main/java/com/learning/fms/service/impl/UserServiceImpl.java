package com.learning.fms.service.impl;

import com.learning.fms.dto.JwtAuthResponseDto;
import com.learning.fms.dto.LoginDto;
import com.learning.fms.dto.RegisterDto;
import com.learning.fms.entity.RoleType;
import com.learning.fms.entity.UserRole;
import com.learning.fms.exception.FoundException;
import com.learning.fms.exception.InvalidInputException;
import com.learning.fms.exception.NotFoundException;
import com.learning.fms.mapper.UserMapper;
import com.learning.fms.repository.UserRepository;
import com.learning.fms.repository.UserRoleRepository;
import com.learning.fms.security.JwtTokenProvider;
import com.learning.fms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserMapper userMapper;

    @Override
    public JwtAuthResponseDto login(LoginDto loginDto) {
        var user = userRepository.findByEmail(loginDto.getEmail().trim());

        if (user == null) {
            throw new NotFoundException("USER_NOT_FOUND", "User not found with email: " + loginDto.getEmail());
        }

        if (!passwordEncoder.matches(loginDto.getPassword().trim(), user.getPassword())) {
            throw new InvalidInputException("INCORRECT_PASSWORD", "Entered password is incorrect");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtTokenProvider.generateToken(authentication);

        var roles = userRoleRepository.getByUser_Uuid(user.getUuid())
                .stream().map(userRole -> userRole.getRole().toString())
                .toList();

        return JwtAuthResponseDto.builder()
                .userId(user.getUuid())
                .roles(roles)
                .tokenType("Bearer ")
                .accessToken(token)
                .build();

    }

    @Override
    public void register(RegisterDto registerDto) {
        var userExistsByEmail = userRepository.findByEmail(registerDto.getEmail().trim());
        if(userExistsByEmail != null) {
            throw new FoundException("EMAIL_ALREADY_EXISTS", "User already exists with email: " + registerDto.getEmail());
        }

        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new InvalidInputException("INVALID_INPUT", "Passwords are not matched");
        }

        var user = userMapper.registerDtoToUser(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword().trim()));

        var savedUser = userRepository.save(user);
        userRoleRepository.save(UserRole.builder()
                .user(savedUser)
                .role(RoleType.CUSTOMER)
                .build());
    }
}
