package com.example.cct.Service;

import com.example.cct.DTO.UserDto;
import com.example.cct.domain.User;
import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService// implements UserDetailsService
{


    private final UserRepository userRepository;


    public User saveUser(User user) {
        validateDuplicate(user);

        return userRepository.save(user);
    }

    private void validateDuplicate(User user) {
        User findUser = (User) userRepository.findAllById(Collections.singleton(user.getId()));
        if (findUser != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        // 비밀번호를 BCrypt 해싱하여 저장
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 비밀번호와 비밀번호 확인이 일치하는지 확인
        if (!user.getPassword().equals(encodedPassword)) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        userRepository.save(user);
    }


    public void deleteUserById(Long Id) {
        // ID로 사용자 검색
        User user = userRepository.findById(Id).orElse(null);

        if (user != null) {
            // 사용자를 삭제로 표시합니다 (사용자 엔터티에 'deleted'라는 필드가 있다고 가정합니다)
            user.setDeleted(true);

            // 업데이트된 사용자 저장 (소프트 삭제)
            userRepository.save(user);
        }
    }

    public void updateUser(UserDto userDto) {
        // ID로 사용자 검색
        User user = userRepository.findById(userDto.getId()).orElse(null);

        if (user != null) {
            // DTO를 기반으로 사용자 필드 업데이트
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setAddress(userDto.getAddress());
            user.setPhone(userDto.getPhone());
            user.setBrith(userDto.getBrith());

            // 업데이트된 사용자 저장
            userRepository.save(user);
        }
    }


    public boolean delet(Long id, String password) {
        return false;
    }
}

