package com.example.cct.Service;

import com.example.cct.Config.CommonResponse;
import com.example.cct.DTO.UserCheck;
import com.example.cct.DTO.UserDto;
import com.example.cct.DTO.UserSignInResponseDto;
import com.example.cct.DTO.UserSignInDto;
import org.springframework.http.ResponseEntity;

public interface UserSignupService {
    public Long signUp(UserDto userDto) throws Exception;

    public UserSignInResponseDto login(UserSignInDto userSignInDto);


    public ResponseEntity<CommonResponse> idCheck (UserCheck userCheck);
    public ResponseEntity<CommonResponse> emailCheck (UserCheck userCheck);
    public ResponseEntity<CommonResponse> phoneCheck (UserCheck userCheck);
}
