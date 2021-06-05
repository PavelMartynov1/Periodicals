package com.example.Periodicals.model.service;

import com.example.Periodicals.Controller.dto.CategoryInput;
import com.example.Periodicals.model.dao.CategoryDao;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.entity.Category;
import java.util.List;
import java.util.Optional;

public class CategoryService {
    private DaoFactory daoFactory;

    public CategoryService() {
        this.daoFactory = DaoFactory.getInstance();
    }
    public Category addCategory(CategoryInput input){
        CategoryDao categoryDao= daoFactory.createCategoryDao();
        return categoryDao.insert(buildCategory(input));
    }
    public List<Category> findAll(){
        CategoryDao dao=daoFactory.createCategoryDao();
        return dao.findAll();
    }
    public Optional<Category> findById(int id){
        CategoryDao dao=daoFactory.createCategoryDao();
        return dao.find(id);
    }
    public Category buildCategory(CategoryInput input){
        Category category=new Category();
        category.setName(input.getName());
        category.setDescription(input.getDescription());
        return  category;
    }
}
