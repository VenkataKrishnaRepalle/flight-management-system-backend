package com.learning.fms.Controller;

import com.learning.fms.dto.JwtAuthResponseDto;
import com.learning.fms.dto.LoginDto;
import com.learning.fms.dto.RegisterDto;
import com.learning.fms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
