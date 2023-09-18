package com.example.cct.Service;


import com.example.cct.DTO.UserDto;
import com.example.cct.domain.User;
import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserSignupServiceImpl implements UserSignupService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long signUp(UserDto userDto) throws Exception {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw  new Exception("이미 존재하는 이메일입니다.");
        }
        if(!userDto.getPassword().equals(userDto.getPasswordChk())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        User user = userRepository.save(userDto.toEntity());
        user.encodePassword(passwordEncoder);

        user.addUserAuthority();

        return user.getId();
        }


    @Override
    public String login(Map<String, String> members) {

         User member = userRepository.findByEmail(members.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));

        String password = members.get("password");
        //if (! membe(passwordEncoder, password) {
       //     throw new IllegalArgumentException("잘못된 비밀번호입니다.");
       // }

        List<String> roles = new ArrayList<>();
        roles.add(member.getRoles().name());
        return TokenProvider.createToken(member.getName(), roles);
    }

    @Override
    public boolean delet(Long id, String password){
        User user = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("아이디가 존재하지 않습니다."));
        if(passwordEncoder.matches(password, user.getPassword())){
            userRepository.delete(user);
            return true;
        }else{
            return false;
        }
    }
}

