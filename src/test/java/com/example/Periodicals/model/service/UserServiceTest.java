package com.example.Periodicals.model.service;

import com.example.Periodicals.Controller.dto.PaymentInput;
import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.PasswordManager;
import com.example.Periodicals.exceptions.NoSuchUser;
import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.UserDao;
import com.example.Periodicals.model.entity.Gender;
import com.example.Periodicals.model.entity.Payment;
import com.example.Periodicals.model.entity.Status;
import com.example.Periodicals.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private UserDao userDao;
    @Mock
    private DBManager dbManager;
    @InjectMocks
    private UserService service;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void setUserStatusShouldReturnTrue() {
        Status status=Status.ACTIVE;
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.setStatus(isA(Status.class),isA(Long.class))).thenReturn(true);
        boolean result=service.setUserStatus(status,2);
        assertTrue(result);
    }
    @Test
    public void setUserStatusShouldReturnFalse() {
        Status status=Status.ACTIVE;
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.setStatus(isA(Status.class),isA(Long.class))).thenReturn(false);
        boolean result=service.setUserStatus(status,2);
        assertFalse(result);
    }

    @Test
    public void findByMail() {
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.searchByMail("mail")).thenReturn(new ArrayList<>());
        assertTrue(service.findByMail("mail") instanceof  List);


    }

    @Test
    public void findByIdShoulReturnUser() {
        Long id=1L;
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.find(isA(Long.class))).thenReturn(Optional.of(new User()));
        assertTrue(service.findById(id) instanceof User);
    }
    @Test(expected= NoSuchUser.class)
    public void findByIdShouldThrowException(){
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.find(isA(Long.class))).thenReturn(Optional.empty());
        service.findById(1L);
    }

    @Test
    public void findUser() {
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.findByEmail(isA(String.class))).thenReturn(Optional.of(new User()));
        assertTrue(service.findUser(new RegInput.Builder().setEmail("mail").build()).isPresent());
    }

    @Test
    public void findAll() {

        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.findAll()).thenReturn(new ArrayList<>());
        assertTrue(service.findAll() instanceof List);

    }
    @Test
    public void addMoneyShouldReturnFalse(){
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)){
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(factory.createUserDao(any())).thenReturn(userDao);
            when(userDao.addMoney(new PaymentInput())).thenReturn(false);
            boolean res= service.addMoney(new PaymentInput());
            assertFalse(res);
        }
    }
    @Test
    public void addMoneyShouldReturnTrue(){
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)){
            PaymentInput payment=new PaymentInput();
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(factory.createUserDao(any())).thenReturn(userDao);
            when(userDao.addMoney(isA(PaymentInput.class))).thenReturn(true);
            boolean res= service.addMoney(payment);
            assertTrue(res);
        }
    }

    @Test
    public void getBalanceShouldReturnBigDecimal() {
        BigDecimal value;

        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)){
            User user=new User();
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(factory.createUserDao(any())).thenReturn(userDao);
            when(userDao.getBalance(any())).thenReturn(new BigDecimal("1"));
            value=service.getBalance(user);
            assertNotNull(value);
        }
    }
    @Test
    public void checkPasswordShouldReturnTrueIfNotEqual() {
        User user=new User.Builder().setPassword(
                new PasswordManager().hashPassword("pass"))
                .build();
        String pass="newpass";
        boolean result;
        result=service.checkPassword(user,pass);
        assertFalse(result);

    }
    @Test
    public void checkPasswordShouldReturnTrueIfEqual() {
        User user=new User.Builder().setPassword(
                new PasswordManager().hashPassword("pass"))
                .build();
        String pass="pass";
        boolean result;
        result=service.checkPassword(user,pass);
        assertTrue(result);

    }
    @Test
    public void insertNewUserShouldReturnUserIfNew() {
        RegInput input=new RegInput.Builder()
                .setEmail("mail")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setGender(Gender.MALE)
                .setFirstPassword("pass")
                .build();
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)){
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(factory.createUserDao(any())).thenReturn(userDao);
            when(userDao.emailExists("mail")).thenReturn(false);
           when(userDao.insert(isA(User.class))).thenReturn(new User());
            assertNotNull(service.insertNewUser(input));
        }
    }
    @Test
    public void insertNewUserShouldReturnNullIfExists() {
        RegInput input=new RegInput.Builder()
                .setEmail("mail")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setGender(Gender.MALE)
                .setFirstPassword("pass")
                .build();
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)){
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(factory.createUserDao(any())).thenReturn(userDao);
            when(userDao.emailExists("mail")).thenReturn(true);
        assertNull(service.insertNewUser(input));
        }
    }

    @Test
    public void userIsNotBannedShouldReturnTrueIfIs() {
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.getUserStatus(isA(String.class))).thenReturn(Status.ACTIVE);
        boolean result=service.userIsNotBanned("anymail");
        assertTrue(result);
    }

    @Test
    public void userIsNotBannedShouldReturnFalseBanned() {
        when(factory.createUserDao()).thenReturn(userDao);
        when(userDao.getUserStatus(isA(String.class))).thenReturn(Status.BANNED);
        boolean result=service.userIsNotBanned("anymail");
        assertFalse(result);
    }
}