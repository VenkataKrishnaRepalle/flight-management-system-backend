package com.learning.fms.service;

import com.learning.fms.dto.JwtAuthResponseDto;
import com.learning.fms.dto.LoginDto;
import com.learning.fms.dto.RegisterDto;
import com.learning.fms.entity.User;

public interface UserService {
    JwtAuthResponseDto login(LoginDto loginDto);

    void register(RegisterDto registerDto);

    User getMe();
}
