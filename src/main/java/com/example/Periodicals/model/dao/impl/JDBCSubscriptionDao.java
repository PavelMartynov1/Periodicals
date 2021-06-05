package com.example.Periodicals.model.dao.impl;

import com.example.Periodicals.exceptions.NoMagazineWasFound;
import com.example.Periodicals.exceptions.NoSuchUser;
import com.example.Periodicals.exceptions.NotEnoughFunds;
import com.example.Periodicals.model.dao.*;
import com.example.Periodicals.model.entity.Payment;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCSubscriptionDao implements SubscriptionDao {
    DaoFactory factory = DaoFactory.getInstance();

    private final String FIND_SUB = "SELECT * FROM subscriptions join  users on subscriptions.user_id=users.user_id join payments on subscriptions.payment_id=payments.payment_id join magazines on subscriptions.magazine_id=magazines.magazine_id join categories on magazines.periodical_category_id= categories.category_id where subscriptions.user_id=? and subscriptions.magazine_id=? ";
    private final String SUBSCRIBE_USER = "INSERT INTO subscriptions (magazine_id,user_id,payment_id) VALUES (?,?,?)";
    private final String FIND_USER_SUBS="SELECT * FROM subscriptions join  users on subscriptions.user_id=users.user_id join payments on subscriptions.payment_id=payments.payment_id join magazines on subscriptions.magazine_id=magazines.magazine_id join categories on magazines.periodical_category_id= categories.category_id where subscriptions.user_id=?";
    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger();

    public JDBCSubscriptionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Subscription> find(long id) {
        return Optional.empty();
    }

    @Override
    public List<Subscription> findAll() {
        return null;
    }

    @Override
    public Subscription insert(Subscription subscription) {
        return null;
    }


    @Override
    public Subscription update(Subscription subscription) {
        return null;
    }

    @Override
    public Subscription delete(Subscription subscription, User user) {
        return null;
    }

    @Override
    public Optional<Subscription> findSubscription(User user, long periodical_id) {
        Optional<Subscription> result = Optional.empty();
        PreparedStatement stmt;
        ResultSet rs;
        Connection conn;
        try {
            conn = DBManager.getConnection();
            stmt = conn.prepareStatement(FIND_SUB);
            stmt.setLong(1, user.getId());
            stmt.setLong(2, periodical_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = Optional.of(buildSubscription(rs, user));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public Subscription subscribe(Subscription sub) {
        PreparedStatement statement;
        User user=sub.getSubscriber();
        BigDecimal magazinePrice = sub.getMagazine().getPrice();//getMagazinePrice(periodicalId);
        BigDecimal userBalance = getUserBalance(user);
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(magazinePrice);

        if (magazinePrice != null && userBalance != null) {
            if (itIsPossible(magazinePrice, userBalance)) {
                try {
                    connection.setAutoCommit(false);
                    UserDao dao = DaoFactory.getInstance().createUserDao(connection);
                    dao.substractMoney(user, magazinePrice);
                    PaymentDao paymentDao = DaoFactory.getInstance().createPaymentDao(connection);
                    payment = paymentDao.createPayment(payment);
                    sub.setPayment(payment);
                    statement = connection.prepareStatement(SUBSCRIBE_USER);
                    statement.setLong(1, sub.getMagazine().getId());
                    statement.setLong(2, user.getId());
                    statement.setLong(3, payment.getId());
                    connection.commit();
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                   LOGGER.log(Level.ERROR,e);
                    DBManager.rollback(connection);
                }
                finally {
                    DBManager.closeConnection(connection);
                }
            } else {
                throw new NotEnoughFunds("user does not have enough funds", user);
            }
        }
        return sub;

    }

    private BigDecimal getUserBalance(User user) {
        UserDao userDao = factory.createUserDao(connection);

        BigDecimal userBalance = null;
        try {
            userBalance = userDao.getBalance(user);
        } catch (NoSuchUser e) {
            LOGGER.log(Level.ERROR,e);
        }
        return userBalance;
    }
    public List<Subscription> findUserSubs(User user){
        PreparedStatement stmt;
        ResultSet rs;
        List<Subscription> list=new ArrayList<>();
        try{
            stmt=connection.prepareStatement(FIND_USER_SUBS);
            stmt.setLong(1,user.getId());
            rs=stmt.executeQuery();
            while(rs.next()){
                list.add(buildSubscription(rs,user));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return list;
    }

    private boolean itIsPossible(BigDecimal magazinePrice, BigDecimal userBalance) {
        int result = userBalance.compareTo(magazinePrice);
        if (result != -1) {
            return true;
        }
        return false;
    }

    private Subscription buildSubscription(ResultSet rs, User user) throws SQLException {
        Subscription sub = new Subscription();
        sub.setId(rs.getLong("sub_id"));
        sub.setSubscriber(user);
        sub.setMagazine(new JDBCMagazineDao().buildMagazine(rs));
        sub.setPayment(buildPayment(rs, user));
        return sub;
    }


    private Payment buildPayment(ResultSet rs, User user) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getLong("payment_id"));
        payment.setUser(user);
        payment.setAmount(rs.getBigDecimal("total_price"));
        return payment;
    }


    @Override
    public void close() throws Exception {
        DBManager.closeConnection(connection);
    }
}
