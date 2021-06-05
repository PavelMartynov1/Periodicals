package com.example.Periodicals.model.dao.impl;

import com.example.Periodicals.model.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public MagazineDao createMagazineDao() {
        return new JDBCMagazineDao();

    }

    @Override
    public MagazineDao createMagazineDao(Connection connection) {
        return new JDBCMagazineDao(connection);
    }

    @Override
    public UserDao createUserDao(Connection connection) {
        return new JDBCUserDao(connection);
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao();
    }

    @Override
    public CategoryDao createCategoryDao() {
        try {
            return new JDBCCategoryDao(DBManager.getConnection());
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException();
        }
    }

    @Override
    public SubscriptionDao createSubscriptionDao(Connection connection) {
        return new JDBCSubscriptionDao(connection);
    }

    @Override
    public PaymentDao createPaymentDao(Connection connection) {
        return new JDBCPaymentDao(connection);
    }
}
