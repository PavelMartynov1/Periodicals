package com.example.Periodicals.Controller.dto;

import com.example.Periodicals.model.entity.User;

import java.time.LocalDateTime;

public class PaymentInput {
   private String money;
    private User user;
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
