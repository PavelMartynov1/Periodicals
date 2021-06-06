package com.example.Periodicals.model.dao.impl;

import com.example.Periodicals.Controller.dto.PaymentInput;
import com.example.Periodicals.exceptions.NoSuchUser;
import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.UserDao;
import com.example.Periodicals.model.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    public static final String INSERT_USER = "INSERT INTO users(email, password, role_id,first_name,last_name,gender) VALUES(?,?,?,?,?,?)";
    public static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String ADD_MONEY = "UPDATE users SET balance =balance+? where user_id=?";
    private static final String GET_USER_BALANCE = "SELECT balance FROM users where user_id=?";
    private static final String SUBSTRACT_MONEY = "UPDATE users SET balance =balance-? where user_id=?";
    private final String FIND_ALL_BY_MAIL = "SELECT * FROM users where email LIKE ?";
    private final String FIND_ALL = "SELECT * FROM users;";
    private final String FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";
    private final String SET_USER_STATUS = "UPDATE  users set status=? where user_id=?";
    private final String GET_USER_STATUS="SELECT status FROM users where email=?";
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;

    public JDBCUserDao() {
    }

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = Optional.empty();
        PreparedStatement stmt;
        ResultSet rs;

        try {
            stmt = connection.prepareStatement("SELECT * FROM USERS WHERE email=?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = Optional.of(buildUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.debug("findByEmail  ", e);
        }
        return user;
    }

    private Role getRole(int role_id) {
        if (role_id == 1) {
            return Role.USER;
        }
        return Role.ADMIN;

    }

    @Override
    public Optional<User> find(long user_id) {
        Optional<User> user = Optional.empty();
        PreparedStatement statement;
        ResultSet rs;
        try {
            statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setLong(1, user_id);
            rs = statement.executeQuery();
            if (rs.next()) {
                user = Optional.of(buildUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        PreparedStatement stmt;
        ResultSet rs;
        List<User> list = new ArrayList<>();
        try {
            stmt = connection.prepareStatement(FIND_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return list;
    }

    @Override
    public User insert(User user) {
        ResultSet keys;
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRoleId());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender().toString());
            stmt.executeUpdate();
            keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
            close(keys);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(User user, User user1) {
        return null;
    }


    @Override
    public boolean emailExists(String email) {
        PreparedStatement stmt;
        try {

            stmt = connection.prepareStatement(FIND_BY_EMAIL);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                close(rs);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean addMoney(PaymentInput payment) {
        boolean flag = false;
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(ADD_MONEY);
            stmt.setBigDecimal(1, new BigDecimal(payment.getMoney()));
            stmt.setLong(2, payment.getUser().getId());
            if (stmt.executeUpdate() != 0) {
                flag = true;
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return flag;
    }

    @Override
    public boolean substractMoney(User user, BigDecimal value) {
        boolean flag = false;
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(SUBSTRACT_MONEY);
            stmt.setBigDecimal(1, value);
            stmt.setLong(2, user.getId());
            if (stmt.executeUpdate() != 0) {
                flag = true;
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return flag;
    }

    @Override
    public BigDecimal getBalance(User user) {
        PreparedStatement stmt ;
        ResultSet rs;
        BigDecimal balance = null;
        try {
            stmt = connection.prepareStatement(GET_USER_BALANCE);
            stmt.setLong(1, user.getId());
            rs = stmt.executeQuery();
            if (rs.next()) {
                balance = rs.getBigDecimal("balance");
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new NoSuchUser(e.getMessage(), e, user);
        }
        return balance;
    }

    @Override
    public List<User> searchByMail(String mail) {
        PreparedStatement stmt ;
        ResultSet rs;
        List<User> list = new ArrayList<>();
        try {
            stmt = connection.prepareStatement(FIND_ALL_BY_MAIL);
            stmt.setString(1, buildLikeMail(mail));
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return list;
    }

    @Override
    public boolean setStatus(Status status, long user_id) {
        PreparedStatement stmt;
        boolean flag = false;
        try {
            stmt = connection.prepareStatement(SET_USER_STATUS);
            stmt.setString(1, status.toString().toUpperCase());
            stmt.setLong(2, user_id);
            if (stmt.executeUpdate() != 0) {
                flag = true;
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return flag;

    }

    @Override
    public Status getUserStatus(String email) {
        PreparedStatement statement;
        ResultSet rs;
        Status status=null;
        try{
            statement=connection.prepareStatement(GET_USER_STATUS);
            statement.setString(1,email);
            rs=statement.executeQuery();
            if(rs.next()){
                status=Status.valueOf(rs.getString("status"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return status;
    }

    @Override
    public boolean start() {
        try{
            connection=DBManager.getConnection();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }

    private String buildLikeMail(String mail) {
        StringBuilder sb = new StringBuilder(mail);
        return sb.insert(0, "%").append("%").toString();
    }

    public void finish() {
        DBManager.closeConnection(connection);
    }

    public void close(AutoCloseable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private User buildUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(getRole(rs.getInt("role_id")));
        user.setGender(Gender.valueOf(rs.getString("gender")));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setSingInDate(LocalDate.now());
        user.setBalance(rs.getBigDecimal("balance"));
        user.setStatus(Status.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)));
        return user;
    }
}
