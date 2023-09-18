package com.example.cct.Controller;

import com.example.cct.DTO.UserDto;
import com.example.cct.Service.UserService;
import com.example.cct.Service.UserSignupService;
import com.example.cct.domain.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserSignupService userSignupService;
    private final UserService userService;


    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody UserDto dto) throws Exception {
        return userSignupService.signUp(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> member) {
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

    // 회원 수정
    @PutMapping("/update")

    public String updateUser(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // User 클래스를 사용
        User name = user;
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
    //회원 탈퇴
    @PostMapping("/delet/user")
    public String memberDelet(@RequestParam String password, Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        boolean result = userService.delet(user.getId(), password);
        if (result){
            return "/logout";
        }
        else{
            model.addAttribute("worronfPassword", "비밀번호가 맞지 않습니다.");
            return "/delet/user";
        }
    }
}

