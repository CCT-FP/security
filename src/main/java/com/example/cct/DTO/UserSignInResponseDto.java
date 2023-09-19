package com.example.cct.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UserSignInResponseDto {

    private Long id;
    private String userId;
    private String name;
    private String token;
}
