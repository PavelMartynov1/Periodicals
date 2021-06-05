package com.example.Periodicals.model.dao;

import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.MagazineSearchParameters;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MagazineDao extends GenericDao<Magazine>{
    List<Magazine> findByCategoryAndPrice(int id,String type);
    List<Magazine> findByCategory(int id);
    List<Magazine> orderByPrice(String type);
    List<Magazine> nameSearch(String name);
    List<Magazine> findWithParameters(MagazineSearchParameters parameters);
    int countAll();
    int countWithParams(MagazineSearchParameters parameters);
    BigDecimal getPrice(long periodicalId);


}
