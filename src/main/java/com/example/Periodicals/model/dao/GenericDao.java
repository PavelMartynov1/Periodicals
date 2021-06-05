package com.example.Periodicals.model.dao;

import com.example.Periodicals.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface GenericDao<E> {
    Optional<E> find(long id);
    List<E> findAll();
    E insert(E e);
    E update(E e);
    E delete(E e, User user);


}