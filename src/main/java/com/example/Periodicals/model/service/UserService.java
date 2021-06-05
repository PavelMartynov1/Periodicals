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
import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    private DaoFactory factory;

    public UserService() {
        this.factory = DaoFactory.getInstance();
    }
    public boolean setUserStatus(Status status,long user_id){
        UserDao userdao = factory.createUserDao();
        userdao.start();
        return userdao.setStatus(status,user_id);

    }
    public List<User> findByMail(String mail) {
        UserDao userdao = factory.createUserDao();
        boolean status=userdao.start();
        userdao.finish();
        return userdao.searchByMail(mail);
    }
    public User findById(long user_id){
        UserDao userdao = factory.createUserDao();
        userdao.start();
        Optional<User> user;
        user=userdao.find(user_id);
        userdao.finish();
        if(user.isPresent()){
            return  user.get();
        } else {
            throw new NoSuchUser("no user with id "+user_id);
        }
    }
    public boolean addMoney(PaymentInput payment) {
        Connection connection = null;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }
        UserDao userdao = factory.createUserDao(connection);
        boolean result = userdao.addMoney(payment);
        userdao.finish();
        return result;
    }

    public Optional<User> findUser(RegInput input) {
        Connection connection = null;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }
        UserDao userdao = factory.createUserDao(connection);
        Optional<User> result = userdao.findByEmail(input.getEmail());
        userdao.finish();
        return result;

    }

    public List<User> findAll() {
        UserDao userdao = factory.createUserDao();
        userdao.start();
        List<User> list = userdao.findAll();
        userdao.finish();
        return list;
    }

    public BigDecimal getBalance(User user) {
        Connection connection = null;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }
        UserDao userdao = factory.createUserDao(connection);
        BigDecimal balance = userdao.getBalance(user);
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
            LOGGER.log(Level.ERROR,e);
        }
        UserDao userDao = factory.createUserDao(connection);
        boolean exists = userDao.emailExists(user.getEmail());

        if (exists) {
            return null;
        }
        String hashed = new PasswordManager().hashPassword(user.getPassword());
        user.setPassword(hashed);
        user = userDao.insert(user);

        return user;
    }

    public boolean userIsNotBanned(String email) {
        UserDao userdao = factory.createUserDao();
        userdao.start();
        Status status=userdao.getUserStatus(email);
        userdao.finish();
        boolean active=false;
        if(status!=Status.BANNED){
            active= true;
        }

        return active;
    }
}
