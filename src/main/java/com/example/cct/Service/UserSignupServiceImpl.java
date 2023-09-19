package com.example.cct.Service;


import com.example.cct.Config.CommonResponse;
import com.example.cct.DTO.UserCheck;
import com.example.cct.DTO.UserDto;
import com.example.cct.DTO.UserSignInDto;
import com.example.cct.DTO.UserSignInResponseDto;
import com.example.cct.domain.User;
import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Optional<User> testUser = userRepository.findByUserId(userDto.getUserId());
        User user = userDto.toEntity();
        user.encodePassword(passwordEncoder);
        user.addUserAuthority(userDto);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public ResponseEntity<CommonResponse> idCheck(UserCheck userCheck) {

        try {
            Optional<User> checkUser = userRepository.findByUserId(userCheck.getUserCheck());
        } catch (IllegalArgumentException il) {
            return ResponseEntity.ok(new CommonResponse("ss", 200));

            //      userRepository.findByUserId(userCheck.getUserCheck())
            //             .ifPresent(m -> {
            //                         throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
            //                    });

        }  return ResponseEntity.ok(new CommonResponse("FAIL", 403));
    }

    @Override
    public ResponseEntity<CommonResponse> emailCheck(UserCheck userCheck) {
        try {
            Optional<User> checkUser = userRepository.findByEmail(userCheck.getUserCheck());
        } catch (IllegalArgumentException il) {
            return ResponseEntity.ok(new CommonResponse("FAIL", 403));
        }return ResponseEntity.ok(new CommonResponse("SU", 200));
    }

    @Override
    public ResponseEntity<CommonResponse> phoneCheck(UserCheck userCheck) {
        try {
            Optional<User> checkUser = userRepository.findByEmail(userCheck.getUserCheck());
        } catch (IllegalArgumentException il) {
            return ResponseEntity.ok(new CommonResponse("FAIL", 403));
        }return ResponseEntity.ok(new CommonResponse("SU", 200));
    }

    @Override
    public UserSignInResponseDto login(UserSignInDto userSignInDto) {
        UserSignInResponseDto userSignInResponseDto = new UserSignInResponseDto();
        try {
            Optional<User> member = userRepository.findByUserId(userSignInDto.getUserId());

            List<String> roles = new ArrayList<>();
            roles.add(member.get().getRoles().name());
            userSignInResponseDto.setToken(TokenProvider.createToken(member.get().getUserId(), roles));
            userSignInResponseDto.setUserId(member.get().getUserId());
            userSignInResponseDto.setName(member.get().getName());
            userSignInResponseDto.setId(member.get().getId());

            return userSignInResponseDto;
        }


        catch (NullPointerException e) {  //존재하지 않는 로그인시 예외 처리
            e.printStackTrace(); // 에러 로그를 출력
            //   sendErrorResponse(HttpServletResponse.SC_CREATED, "존재하지 않는 아이디입니다.");
        }

        return null;
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

