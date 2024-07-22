package com.vietcuong.simpleSecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    // Defines a many-to-one relationship between the Token entity and the
    // User entity
    // The foreign key column in the "token" table is named "user_id"
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(boolean loggedOut) {
        this.loggedOut = loggedOut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "id: " + this.getId() + ", token: " + this.getToken() + ", " + "loggedOut: " + this.isLoggedOut() + ", " + "user: " + this.getUser()
                .getUsername();
    }
}
