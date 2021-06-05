package com.example.Periodicals.exceptions;

import com.example.Periodicals.model.entity.User;

public class NoSuchUser extends RuntimeException{
    private User user;

    public NoSuchUser(String message, Throwable cause, User user) {
        super(message, cause);
        this.user = user;
    }
    public NoSuchUser(String message){
        super(message);
    }

    public User getUser() {
        return user;
    }
}
