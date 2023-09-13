package com.example.cct.DTO;


import com.example.cct.domain.User;
import com.example.cct.domain.model.MemberRole;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class UserDto  {
        @NotBlank(message = "이름은 필수 입력 값입니다.")
         private Long id;

        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String name;

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식으로 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
        private String password;

        @NotBlank(message = "주소는 필수 입력 값입니다.")
        private String address;

        @NotBlank(message = "전화번호를 입력해주세요.")
        private String phone;
        @NotBlank(message = "생년월일을 입력해주세요.")
        private String brith;

        @NotEmpty(message = "자신의 권한을 선택해주세요")
        private MemberRole roles;

        @NotBlank(message = "비밀번호 확인란을 입력해주세요")
        private String PasswordChk;

    @Builder
    public UserDto(Long id, String name, String password, String phone, String brith, String email, String address, MemberRole roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.brith = brith;
        this.email = email;
        this.address = address;
        this.roles = roles;
    }

    @Builder
    public User toEntity(){
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .roles(MemberRole.USER)
                .build();
    }
    }

