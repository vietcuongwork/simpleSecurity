package com.vietcuong.simpleSecurity.repository;


import com.vietcuong.simpleSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    void deleteByUsername(String username);
}