package com.learning.fms.mapper;

import com.learning.fms.dto.RegisterDto;
import com.learning.fms.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User registerDtoToUser(RegisterDto registerDto);
}
