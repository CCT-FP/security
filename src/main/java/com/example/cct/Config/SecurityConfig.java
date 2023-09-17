package com.example.cct.Config;

import com.example.cct.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin().disable()
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/join").permitAll()
                .antMatchers("/user").hasRole("USER")
                .anyRequest().authenticated();
//        http
//                .formLogin()
//                    .loginPage("/user/login")
//                    .defaultSuccessUrl("/")
//                    .usernameParameter("email")
//                    .failureUrl("/user/login/error")
//                    .and()
//                .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
//                    .logoutSuccessUrl("/")
//        ;
//
//        http
//                .authorizeRequests()
//                .mvcMatchers("/", "/uesr/**", "/item/**", "/assets/**", "/h2-console/**").permitAll()
//                .mvcMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                ;
//
//        http
//                .exceptionHandling()
//                .authenticationEntryPoint(new AAuthenticationEntryPoint())
//        ;
//        http
//                .authorizeRequests()
//                .antMatchers("/public/**").permitAll() // 모든 사용자에게 허용
//                .antMatchers("/private/**").authenticated() // 인증된 사용자에게만 허용
//                .and()
//                .formLogin()
//                .loginPage("/login") // 로그인 페이지 지정
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout") // 로그아웃 URL 지정
//                .logoutSuccessUrl("/login?logout") // 로그아웃 후 리다이렉트할 URL
//                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/file/**",
            "/image/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/h2/**"
    };


@Override
public void configure(WebSecurity webSecurity) throws Exception{
    webSecurity.ignoring().antMatchers(AUTH_WHITELIST);
}
}
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void configureLogin(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/signup").permitAll() // 회원가입 페이지는 모든 사용자에게 허용
//                .anyRequest().authenticated() // 나머지 요청은 인증된 사용자에게만 허용
//                .and()
//                .formLogin()
//                .loginPage("/login") // 로그인 페이지 경로
//                .defaultSuccessURL("/home") // 로그인 성공 시 이동할 경로
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // 다른 설정
//                .and()
//                .logout()
//                .logoutUrl("/logout") // 로그아웃 URL 지정 (기본값: /logout)
//                .logoutSuccessUrl("/login") // 로그아웃 후 이동할 URL 지정
//                .permitAll(); // 로그아웃은 모든 사용자에게 허용
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//}