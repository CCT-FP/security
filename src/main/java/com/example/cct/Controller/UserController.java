package com.example.cct.Controller;

import com.example.cct.Config.CommonResponse;
import com.example.cct.DTO.UserCheck;
import com.example.cct.DTO.UserDto;
import com.example.cct.DTO.UserSignInDto;
import com.example.cct.DTO.UserSignInResponseDto;
import com.example.cct.Service.UserService;
import com.example.cct.Service.UserSignupService;
import com.example.cct.domain.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.cct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserSignupService userSignupService;
    private final UserService userService;


    //@CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@RequestBody UserDto dto) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text","xml", Charset.forName("UTF-8")));
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
        return userSignupService.signUp(dto);
    }

    @PostMapping("/login")
    public UserSignInResponseDto login(@RequestBody UserSignInDto userSignInDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text","xml", Charset.forName("UTF-8")));
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
        return userSignupService.login(userSignInDto);
    }

    @PostMapping("/idCheck")
    public ResponseEntity<CommonResponse> idCheck(@RequestBody UserCheck userCheck){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text","xml", Charset.forName("UTF-8")));
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
        return userSignupService.idCheck(userCheck);
    }
    @PostMapping("/emailCheck")
    public ResponseEntity<CommonResponse> emailCheck(@RequestBody UserCheck userCheck){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text","xml", Charset.forName("UTF-8")));
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
        return userSignupService.idCheck(userCheck);
    }
    @PostMapping("/phoneCheck")
    public ResponseEntity<CommonResponse> phoneCheck(@RequestBody UserCheck userCheck){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text","xml", Charset.forName("UTF-8")));
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
        return userSignupService.idCheck(userCheck);
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

    //마이페이지 이동을 위한 사용자 조회
    @GetMapping("/user/{userId}/profile")
    public String myPage(@PathVariable Long userId, Model model) {
        User user = userService.getUserId(userId);
        model.addAttribute("user", user);
        return "profile";
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

