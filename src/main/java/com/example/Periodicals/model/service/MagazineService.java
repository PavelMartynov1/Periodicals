package com.example.Periodicals.model.service;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.MagazineDao;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.MagazineSearchParameters;
import com.example.Periodicals.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MagazineService {
    private  DaoFactory daoFactory;
    MagazineDao magDao ;

    public MagazineService() {
        this.daoFactory = DaoFactory.getInstance();
       this.magDao= daoFactory.createMagazineDao();
    }

    public Optional<Magazine> find(long id) {
        return magDao.find(id);
    }
    public int getCountWithParams(MagazineSearchParameters parameters){
        return magDao.countWithParams(parameters);
    }
    public int countAll(){
        return magDao.countAll();
    }
    public boolean delete(Magazine magazine, User user){
        boolean flag=false;
        Optional<Magazine> deletedMagazine=Optional.ofNullable(magDao.delete(magazine,user));
        if(deletedMagazine.isPresent()){
            flag=true;
        }
        return flag;
    }
    public List<Magazine> findAll() {
         return magDao.findAll();
    }
    public List<Magazine> findBySearchParameters(MagazineSearchParameters parameters){

        return magDao.findWithParameters(parameters);
    }
    public boolean addMagazine(MagazineCreationInput input) {
        boolean flag = false;
        Magazine mag = magDao.insert(buildMagazine(input));
        if (mag != null) {
            flag = true;
        }
        return flag;
    }



    public boolean updateMagazine(MagazineCreationInput input) {
        Optional<Magazine> magazine=Optional.ofNullable(magDao.update(buildMagazine(input)));
        if(magazine.isPresent()) {
            return true;
        }
        return false;
    }

    public Magazine buildMagazine(MagazineCreationInput input) {
        return new Magazine.Builder()
                .setId(input.getId())
                .setName(input.getName())
                .setCategory(input.getCategory())
                .setDescription(input.getDescription())
                .setPrice(new BigDecimal(input.getPrice()))
                .setPublisher(input.getPublisher()).build();

    }
}
