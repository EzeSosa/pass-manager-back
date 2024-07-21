package com.esosa.pass_manager.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class Password {
    private String name;
    private String password;

    @ManyToOne
    private User user;

    @Id
    private final UUID id = UUID.randomUUID();

    public Password(String name, String password, User user) {
        this.name = name;
        this.password = password;
        this.user = user;
    }

    public Password() {}

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public User getUser() {
        return this.user;
    }

    public UUID getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }
}