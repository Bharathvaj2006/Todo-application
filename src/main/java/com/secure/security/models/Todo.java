package com.secure.security.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    String name;


    boolean isCompleted;


    public long getId() {
        return id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Todo setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

