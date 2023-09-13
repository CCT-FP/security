package com.example.cct.Service;

import com.example.cct.DTO.UserDto;

import java.util.Map;

public interface UserSignupService {
    public Long signUp(UserDto userDto) throws Exception;
    public String login(Map<String,String> user);
}
