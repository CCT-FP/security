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
public class UserDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
    private String password;

    private String address;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone;

    private String brith;

    private MemberRole roles;

    //담당자명
    private String cname;

    @Builder
    public UserDto(String userId, String name, String password, String phone, String brith, String email, String address, MemberRole roles, String cname) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.brith = brith;
        this.email = email;
        this.address = address;
        this.roles = roles;
        this.cname = cname;
    }

    @Builder
    public User toEntity() {
        //프리랜서
        if (this.roles.equals("USER")) {
            return User.builder()
                    .userId(userId)
                    .name(name)
                    .phone(phone)
                    .brith(brith) //
                    .email(email)
                    .password(password)
                    .roles(MemberRole.USER)
                    .build();
        }
        //기업
        else {
            return User.builder()
                    .userId(userId)
                    .phone(phone)
                    .cname(cname)//담당자 명
                    .address(address)
                    .email(email)
                    .name(name) //회사명
                    .password(password)
                    .roles(MemberRole.ADMIN)
                    .build();
        }
    }
}

