package com.example.Periodicals.model.dao;

import com.example.Periodicals.Controller.dto.PaymentInput;
import com.example.Periodicals.model.entity.Status;
import com.example.Periodicals.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User>{
    Optional<User> findByEmail(String email);
    boolean emailExists(String email);
    boolean addMoney(PaymentInput payment);
    boolean substractMoney(User user,BigDecimal value);
    BigDecimal getBalance(User user);
    List<User> searchByMail(String mail);
    boolean setStatus(Status status, long user_id);
    Status getUserStatus(String email);
    boolean start();
    void finish();

}