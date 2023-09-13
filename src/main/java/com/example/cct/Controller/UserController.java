package com.example.cct.Controller;

import com.example.cct.DTO.UserDto;
import com.example.cct.Service.UserService;
import com.example.cct.Service.UserSignupService;
import com.example.cct.domain.User;
import javax.validation.Valid;

import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserSignupService userSignupService;
   private final UserService userService;
   private final UserService passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody UserDto dto) throws Exception{
        return userSignupService.signUp(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String ,String> member){
        return userSignupService.login(member);
    }


    @GetMapping(value = "/login")
    public String loginUser() {
        return "/User/UserLogin";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", " 아이디 또는 비밀번호를 확인하세요.");
        return "/user/userLogin";
    }

    // 업데이트
    @GetMapping("/update/user")
    public String updateUser(Model model, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal(); // User 클래스를 사용
        User name = userDetails;
        User userDto = userService.saveUser(name);
        model.addAttribute("user", userDto);

        return "/users/updateUser";
    }


    @PostMapping("/update/user")
    public String updateUser(@Valid UserDto userDto, BindingResult bindingResult, Model model, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "errorPage";
        }
        model.addAttribute("successMessage", "사용자 정보가 업데이트되었습니다.");
        return "succesPage";
    }
}

//    @GetMapping(value = "/join")
//    public String join(Model model) {
//        model.addAttribute("UserDto", new UserDto());
//        return "join/joinForm";
//    }
//
//    @PostMapping(value = "/join")
//    public String joinForm(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "user/userForm";
//        }
//        try {
//            User user = User.createuser(userDto, (PasswordEncoder) passwordEncoder);
//            userService.saveUser(user);
//        } catch (IllegalStateException e) {
//            model.addAttribute("eooroMessage", e.getMessage());
//            return "user/userForm";
//        }
//        return "redirect:/";
//
//    }
