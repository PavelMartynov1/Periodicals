package com.example.Periodicals.model.dao;

import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao extends GenericDao<Subscription> {
    Optional<Subscription> findSubscription(User user, long periodical_id);
    Subscription subscribe(Subscription sub);
    List<Subscription> findUserSubs(User  user);
}
