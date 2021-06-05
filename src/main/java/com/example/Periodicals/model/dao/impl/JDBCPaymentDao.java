package com.example.Periodicals.model.dao.impl;

import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.PaymentDao;
import com.example.Periodicals.model.dao.UserDao;
import com.example.Periodicals.model.entity.Payment;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import com.example.Periodicals.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBCPaymentDao implements PaymentDao {
    private static String CREATE_PAYMENT = "INSERT INTO payments(user_id,total_price) VALUES(?,?)";
    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger();

    public JDBCPaymentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Payment> find(long id) {
        return Optional.empty();
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Payment insert(Payment payment) {
        return null;
    }

    @Override
    public Payment update(Payment payment) {
        return null;
    }

    @Override
    public Payment delete(Payment payment, User user) {
        return null;
    }

    @Override
    public Payment createPayment(Payment payment) throws SQLException {
        PreparedStatement stmt;
        ResultSet rs;
        UserDao userDao = DaoFactory.getInstance().createUserDao(connection);
        try {
            stmt = connection.prepareStatement(CREATE_PAYMENT, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, payment.getUser().getId());
            stmt.setBigDecimal(2, payment.getAmount());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                payment.setId(rs.getLong(1));
            }
            return payment;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new SQLException(e);
        }
    }

    public void finish(){
        DBManager.closeConnection(connection);
    }
}
