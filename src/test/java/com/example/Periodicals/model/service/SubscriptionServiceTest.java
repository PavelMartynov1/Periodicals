package com.example.Periodicals.model.service;

import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.SubscriptionDao;
import com.example.Periodicals.model.dao.impl.JDBCSubscriptionDao;
import com.example.Periodicals.model.dao.impl.JDBCUserDao;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SubscriptionServiceTest {
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private SubscriptionDao dao;
    @Mock
    private DaoFactory daoFactory;
    @Test
    public void checkUserSubscription() throws SQLException {
        MockitoAnnotations.initMocks(this);
        User user=new User();
        when(daoFactory.createSubscriptionDao(any())).thenReturn(new JDBCSubscriptionDao(null));
        when(dao.findSubscription(any(),any())).thenReturn(Optional.empty());
        boolean subscription=subscriptionService.checkUserSubscription(new User(),1L);
        assertFalse(subscription);
    }

    @Test
    public void subscribeUser() {
    }

    @Test
    public void buildSub() {
    }

    @Test
    public void findMagazine() {
    }

    @Test
    public void findUserSubs() {
    }
}