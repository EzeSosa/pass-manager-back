package com.esosa.pass_manager.data.model;

import com.esosa.pass_manager.data.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private final Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Password> passwords = Collections.emptyList();

    @Id
    private final UUID id = UUID.randomUUID();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public UUID getId() {
        return id;
    }
}