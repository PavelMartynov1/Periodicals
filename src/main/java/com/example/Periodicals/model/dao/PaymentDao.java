package com.example.Periodicals.model.dao;

import com.example.Periodicals.model.entity.Payment;

import java.sql.SQLException;

public interface PaymentDao extends GenericDao<Payment> {
    Payment createPayment(Payment payment) throws SQLException;
}
