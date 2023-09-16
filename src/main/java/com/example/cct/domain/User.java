package com.example.cct.domain;
import com.example.cct.DTO.UserDto;
import com.example.cct.domain.model.MemberRole;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name =  "USERS")
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id; // id

    @Column(length = 45)
    private String name; // 회원 성명
    @Column(length = 100)
    private String password; // 비밀번호
    private String PasswordChk;
    private String phone; // 전화번호
    private String brith; //생년월일

    @Column(unique = true , length = 45)
    private String email; // 이메일

    private String address; // 주소

    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private MemberRole roles;

    @Builder
    public User(Long id, String name, String password, String phone, String brith, String email, String address, MemberRole roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.brith = brith;
        this.email = email;
        this.address = address;
        this.roles = roles;
    }


    public static User createuser(UserDto userDto, PasswordEncoder passwordEncoder) {
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .brith(userDto.getBrith())
                .phone(userDto.getPhone())
                .password(passwordEncoder.encode(userDto.getPassword()))  //암호화처리
                .roles(MemberRole.USER)
                .build();
        return user;
    }


    public void addUserAuthority() {
        this.roles = MemberRole.USER;
    }
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }


}
