package com.example.Periodicals.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private long id;
    private User user;
    private BigDecimal amount;
    private LocalDateTime date;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
