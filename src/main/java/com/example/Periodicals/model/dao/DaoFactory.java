package com.example.Periodicals.model.dao;

import com.example.Periodicals.model.dao.impl.JDBCDaoFactory;

import java.sql.Connection;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;

    }


    public abstract MagazineDao createMagazineDao();
    public abstract MagazineDao createMagazineDao(Connection connection);
    public abstract UserDao createUserDao(Connection connection);
    public abstract UserDao createUserDao();
    public abstract CategoryDao createCategoryDao();
    public abstract SubscriptionDao createSubscriptionDao(Connection connection);
    public abstract PaymentDao createPaymentDao(Connection connection);
}
