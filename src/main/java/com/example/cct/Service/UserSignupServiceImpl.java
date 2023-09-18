package com.example.cct.Service;


import com.example.cct.DTO.UserDto;
import com.example.cct.DTO.UserSignInDto;
import com.example.cct.domain.User;
import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserSignupServiceImpl implements UserSignupService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long signUp(UserDto userDto) throws Exception {
        if(userRepository.findByUserId(userDto.getUserId()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        User user = userDto.toEntity();
        user.encodePassword(passwordEncoder);
        user.addUserAuthority(userDto);
        userRepository.save(user);


        return user.getId();
        }


    @Override
    public String login(UserSignInDto userSignInDto) {

         Optional<User> member = userRepository.findByUserId(userSignInDto.getUserId());
           //      orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));

        String password = userSignInDto.getPassword();
        //if (! membe(passwordEncoder, password) {
       //     throw new IllegalArgumentException("잘못된 비밀번호입니다.");
       // }

        List<String> roles = new ArrayList<>();
        roles.add(member.get().getRoles().name());
        return TokenProvider.createToken(member.get().getUserId(), roles);
    }
}

