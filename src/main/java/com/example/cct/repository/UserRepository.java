package com.example.cct.repository;

import com.example.cct.domain.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EntityScan
public interface UserRepository  extends JpaRepository<User , Long> {
//    User findById(Long Id);
    User findByName(String Name);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    Optional<User>  findByPhone(String phone);
}
