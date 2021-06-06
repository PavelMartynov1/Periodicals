package com.example.Periodicals.model.service;

import com.example.Periodicals.Controller.dto.PaymentInput;
import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.PasswordManager;
import com.example.Periodicals.exceptions.NoSuchUser;
import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.UserDao;
import com.example.Periodicals.model.entity.Role;
import com.example.Periodicals.model.entity.Status;
import com.example.Periodicals.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserService {
    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger();

    private DaoFactory factory;

    public UserService() {
        this.factory = DaoFactory.getInstance();
    }

    public boolean setUserStatus(Status status, long user_id) {
        boolean res = false;

        UserDao userdao = factory.createUserDao();
        userdao.start();
        res = userdao.setStatus(status, user_id);
        userdao.finish();
        return res;

    }

    public List<User> findByMail(String mail) {
        List<User> list;

        UserDao userdao = factory.createUserDao();

        userdao.start();
        list = userdao.searchByMail(mail);
        userdao.finish();
        return list;
    }

    public User findById(long user_id) {
        Optional<User> user;

        UserDao userdao = factory.createUserDao();


        userdao.start();
        user = userdao.find(user_id);
        userdao.finish();

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchUser("no user with id " + user_id);
        }
    }

    public boolean addMoney(PaymentInput payment) {
        Connection connection = null;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e);
        }
        boolean result;
        UserDao userdao = factory.createUserDao(connection);
        result = userdao.addMoney(payment);
        userdao.finish();
        return result;
    }



    public Optional<User> findUser(RegInput input) {

        Optional<User> result;

        UserDao userdao = factory.createUserDao();
        userdao.start();
        result = userdao.findByEmail(input.getEmail());
        userdao.finish();
        return result;

    }

    public List<User> findAll() {
        List<User> list;
        UserDao userdao = factory.createUserDao();
        userdao.start();
        list = userdao.findAll();
        userdao.finish();
        return list;
    }

    public BigDecimal getBalance(User user) {
        Connection connection = null;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e);
        }
        BigDecimal balance;

        UserDao userdao = factory.createUserDao(connection);

        balance = userdao.getBalance(user);
        userdao.finish();
        return balance;
    }

    public boolean checkPassword(User user, String password) {

        return user.getPassword().equals(new PasswordManager().hashPassword(password));

    }

    public User insertNewUser(RegInput input) {
        User user = new User.Builder().setEmail(input.getEmail())
                .setFirstName(input.getFirstName())
                .setLastName(input.getLastName())
                .setGender(input.getGender())
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE)
                .setPassword(input.getFirstPassword()).build();

        Connection connection = null;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e);
        }
        boolean exists;

        UserDao userDao = factory.createUserDao(connection);

        exists = userDao.emailExists(user.getEmail());
        if (exists) {
            return null;
        }
        String hashed = new PasswordManager().hashPassword(user.getPassword());
        user.setPassword(hashed);
        user = userDao.insert(user);
        userDao.finish();

        return user;
    }

    public boolean userIsNotBanned(String email) {
        boolean active = false;

        UserDao userdao = factory.createUserDao();

        userdao.start();
        Status status = userdao.getUserStatus(email);
        if (status != Status.BANNED) {
            active = true;
        }


        return active;
    }
}
