package com.example.cct.Service;


import com.example.cct.DTO.UserDto;
import com.example.cct.DTO.UserSignInResponseDto;
import com.example.cct.DTO.UserSignInDto;
import com.example.cct.domain.User;
import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserSignupServiceImpl implements UserSignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long signUp(UserDto userDto) throws Exception {
        User testUser = userRepository.findByUserId(userDto.getUserId());

        if (testUser != null) {
            throw new Exception("이미 존재하는 아이디입니다.");
        }
        User user = userDto.toEntity();
        user.encodePassword(passwordEncoder);
        user.addUserAuthority(userDto);
        userRepository.save(user);
        return user.getId();
    }


    @Override
    public UserSignInResponseDto login(UserSignInDto userSignInDto) {
        UserSignInResponseDto userSignInResponseDto = new UserSignInResponseDto();
        try {
            User member = userRepository.findByUserId(userSignInDto.getUserId());

            List<String> roles = new ArrayList<>();
            roles.add(member.getRoles().name());
            userSignInResponseDto.setToken(TokenProvider.createToken(member.getUserId(), roles));
            userSignInResponseDto.setUserId(member.getUserId());
            userSignInResponseDto.setName(member.getName());
            userSignInResponseDto.setId(member.getId());

            return userSignInResponseDto;
        }


        catch (NullPointerException e) {  //존재하지 않는 로그인시 예외 처리
            e.printStackTrace(); // 에러 로그를 출력
         //   sendErrorResponse(HttpServletResponse.SC_CREATED, "존재하지 않는 아이디입니다.");
        }

        return null;
    }

//    public void sendErrorResponse(int statusCode, String message) {
//        HttpServletResponse response = getResponseDto();
//    }
}

