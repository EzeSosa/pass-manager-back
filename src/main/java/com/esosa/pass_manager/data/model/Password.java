package com.esosa.pass_manager.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Password {
    private String name;
    private String password;

    @Id
    private final UUID id = UUID.randomUUID();

    public Password(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Password() {}

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public UUID getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }
}