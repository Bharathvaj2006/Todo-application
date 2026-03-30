package com.secure.security.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "UserTable")
public class User {
    public User(Long id, String email, String passward) {
        this.id = id;
        this.email = email;
        this.passward = passward;
    }

    public User() {
    }

    @Id
    @GeneratedValue
    private Long id;

    @Email
    private String email;

    private String passward;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassward() {
        return passward;
    }

    public User setPassward(String passward) {
        this.passward = passward;
        return this;
    }
}
