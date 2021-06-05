package com.example.Periodicals.exceptions;

import com.example.Periodicals.model.entity.User;

public class NotEnoughFunds extends RuntimeException{
    private User user;

    public NotEnoughFunds(String message, Throwable cause, User user) {
        super(message, cause);
        this.user = user;
    }
    public NotEnoughFunds(String message, User user) {
        super(message);
        this.user = user;
    }
    public User getUser(){
        return user;
    }

}
