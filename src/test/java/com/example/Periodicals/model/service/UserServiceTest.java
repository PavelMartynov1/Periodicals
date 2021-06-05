package com.example.Periodicals.model.service;

import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.UserDao;
import com.example.Periodicals.model.dao.impl.JDBCUserDao;
import com.example.Periodicals.model.entity.User;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService service;

    @Test
    public void setUserStatus() {
    }

    @Test
    public void findByMail() {
        MockitoAnnotations.initMocks(this);
        List<User> expected = new ArrayList<>();
        expected.add(new User());
        when(factory.createUserDao()).thenReturn(new JDBCUserDao());
        when(userDao.searchByMail("admin@gmail.com")).thenReturn(expected);
        when(userDao.start()).thenReturn(true);
        List<User> users = service.findByMail("admin@gmail.com");
        assertEquals(expected.size(), users.size());
    }

    @Test
    public void findById() {
    }

    @Test
    public void addMoney() {
    }

    @Test
    public void findUser() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void getBalance() {
    }

    @Test
    public void checkPassword() {
    }

    @Test
    public void insertNewUser() {
    }

    @Test
    public void userIsNotBanned() {
    }
}