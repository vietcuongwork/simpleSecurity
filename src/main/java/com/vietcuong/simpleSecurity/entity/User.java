package com.vietcuong.simpleSecurity.entity;

import com.vietcuong.simpleSecurity.validator.ValidDateOfBirth;
import com.vietcuong.simpleSecurity.validator.ValidEmail;
import com.vietcuong.simpleSecurity.validator.ValidFullName;
import com.vietcuong.simpleSecurity.validator.ValidUsername;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "application_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ValidFullName
    @Column(name = "full_name")
    private String fullName;

    @ValidUsername
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ValidEmail
    @Column(name = "email")
    private String email;

    @ValidDateOfBirth
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    // Return a collection with a SimpleGrantedAuthority based on the role name
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
