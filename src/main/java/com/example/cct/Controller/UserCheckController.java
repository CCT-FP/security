package com.example.cct.Controller;

import com.example.cct.Config.CommonResponse;
import com.example.cct.DTO.UserCheck;
import com.example.cct.Service.UserService;
import com.example.cct.Service.UserSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/user")
public class UserCheckController {

    private final UserSignupService userSignupService;
    private final UserService userService;

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
        return userSignupService.idCheck(userCheck);
    }

    @PostMapping("/phoneCheck")
    public ResponseEntity<CommonResponse> phoneCheck(@RequestBody UserCheck userCheck){
        return userSignupService.idCheck(userCheck);
    }

}
