package com.vietcuong.simpleSecurity.repository;


import com.vietcuong.simpleSecurity.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    // Custom query to find all tokens associated with a specific user that
    // are not logged out
    @Query("""
            Select t from Token t inner join User u
            on t.user.id = u.id
            where t.user.id = :userId and t.loggedOut = false
            """)
    List<Token> findAllTokenByUser(Integer userId);

    // Method to find a token by its token string
    Optional<Token> findByToken(String token);

}
