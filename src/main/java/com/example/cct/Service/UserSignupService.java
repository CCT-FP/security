package com.example.cct.Service;

import com.example.cct.DTO.UserDto;
import com.example.cct.DTO.UserSignInDto;

public interface UserSignupService {
    public Long signUp(UserDto userDto) throws Exception;

    public String login(UserSignInDto userSignInDto);


}
