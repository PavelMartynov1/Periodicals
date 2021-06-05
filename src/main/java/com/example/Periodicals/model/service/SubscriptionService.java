package com.example.Periodicals.model.service;

import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.MagazineDao;
import com.example.Periodicals.model.dao.SubscriptionDao;

import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SubscriptionService {
    private static final Logger LOGGER = LogManager.getLogger();
    private DaoFactory factory;

    public SubscriptionService() {
        this.factory = DaoFactory.getInstance();
    }

    public boolean checkUserSubscription(User user, long preiodicalId) {

        SubscriptionDao dao = null;
        try {
            dao = factory.createSubscriptionDao(DBManager.getConnection());
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }
        Optional<Subscription> res = dao.findSubscription(user, preiodicalId);
        boolean flag = false;
        if (res.isPresent()) {
            flag = true;
        }
        return flag;

    }

    public Optional<Subscription> subscribeUser(User user, long periodicalId) {
        Connection connection = null;
        Optional<Subscription> sub;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }
        SubscriptionDao subscriptionDao = factory.createSubscriptionDao(connection);
        sub=Optional.ofNullable(subscriptionDao.subscribe(buildSub(user, periodicalId)));
        return sub;

    }
    public Magazine findMagazine(long periodicalId){
        MagazineDao magazineDao= factory.createMagazineDao();
        Optional<Magazine> magazine=magazineDao.find(periodicalId);
        return magazine.get();
    }
    public List<Subscription> findUserSubs(User user) {
        Connection connection = null;
        try {
            connection = DBManager.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,e);
        }
        SubscriptionDao subscriptionDao = factory.createSubscriptionDao(connection);
        List<Subscription> list=subscriptionDao.findUserSubs(user);
        return list;
    }

    public Subscription buildSub(User user,long periodicalId){
        Subscription sub=new Subscription();
        sub.setSubscriber(user);
        sub.setMagazine(findMagazine(periodicalId));
        return  sub;
    }
}
