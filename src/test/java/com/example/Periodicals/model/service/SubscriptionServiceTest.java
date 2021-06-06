package com.example.Periodicals.model.service;

import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.MagazineDao;
import com.example.Periodicals.model.dao.SubscriptionDao;
import com.example.Periodicals.model.dao.impl.JDBCSubscriptionDao;
import com.example.Periodicals.model.dao.impl.JDBCUserDao;
import com.example.Periodicals.model.entity.Gender;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SubscriptionServiceTest {
    @Mock
    private SubscriptionDao dao;
    @Mock
    private MagazineDao magDao;
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private DBManager dbManager;
    @InjectMocks
    private SubscriptionService subscriptionService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkUserSubscription() {
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)) {
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(daoFactory.createSubscriptionDao(any())).thenReturn(dao);
            when(dao.findSubscription(isA(User.class), isA(Long.class))).thenReturn(Optional.empty());
            boolean result = subscriptionService.checkUserSubscription(new User(), 1L);
            assertFalse(result);
        }
    }

    @Test
    public void checkUserSubscriptionShouldReturnTrue() throws SQLException {
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)) {
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(daoFactory.createSubscriptionDao(any())).thenReturn(dao);
            when(dao.findSubscription(isA(User.class), isA(Long.class))).thenReturn(Optional.of(new Subscription()));
            boolean result = subscriptionService.checkUserSubscription(new User(), 1L);
            assertTrue(result);
        }

    }

    @Test
    public void subscribeUser() {
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)) {
            User user = new User();
            Subscription sub = new Subscription();
            theMock.when(DBManager::getConnection).thenReturn(null);
            when(daoFactory.createSubscriptionDao(any())).thenReturn(dao);
            when(daoFactory.createMagazineDao()).thenReturn(magDao);
            when(magDao.find(isA(Long.class))).thenReturn(Optional.of(new Magazine()));
            when(dao.subscribe(isA(Subscription.class))).thenReturn(sub);
            Optional<Subscription> result = subscriptionService.subscribeUser(user, 1L);
            assertTrue(result.isPresent());
        }
    }

    @Test
    public void buildSub() {
        User user = new User.Builder().setFirstName("name")
                .setPassword("pass")
                .setEmail("mail")
                .setGender(Gender.MALE).build();
        Magazine magazine = new Magazine.Builder()
                .setName("Name")
                .setDescription("descr")
                .setId(2L)
                .build();
        when(daoFactory.createMagazineDao()).thenReturn(magDao);
        when(magDao.find(isA(Long.class))).thenReturn(Optional.of(magazine));
        Subscription sub = subscriptionService.buildSub(user, 1L);
        assertEquals(user, sub.getSubscriber());
        assertEquals(magazine, sub.getMagazine());
    }


    @Test
    public void findUserSubsShouldReturnList() {
        try (MockedStatic<DBManager> theMock = Mockito.mockStatic(DBManager.class)) {
            when(daoFactory.createSubscriptionDao(any())).thenReturn(dao);
            when(dao.findUserSubs(isA(User.class))).thenReturn(new ArrayList<>());
            assertTrue(subscriptionService.findUserSubs(new User()) instanceof List);

        }
    }
}