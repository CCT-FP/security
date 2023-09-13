package com.example.cct.Service;

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
        User findUser = userRepository.findAllById(user.getId());
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


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByName(username); // 사용자 아이디(username)로 사용자를 조회
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//
//        List<GrantedAuthority> authorities = user.getRoles()
//                .stream()
//                .map(role -> new SimpleGrantedAuthority(role))
//                .collect(Collectors.toList());
//
//        return User.builder()
//                .name(user.getEmail())
//                .password(user.getPassword())
//                .authorities(authorities) // 권한(역할) 설정
//                .build();
//    }



//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByName(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException(user.getEmail());
//        }
//        return User.builder()
//                .name(user.getEmail())
//                .password(user.getPassword())
//                .roles(String.join(",", user.getRoles()))
//                .build();
//    }

}

