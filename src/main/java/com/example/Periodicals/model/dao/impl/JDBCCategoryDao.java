package com.example.Periodicals.model.dao.impl;

import com.example.Periodicals.exceptions.CategoryAlreadyExists;
import com.example.Periodicals.model.dao.CategoryDao;
import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.entity.Category;
import com.example.Periodicals.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCategoryDao implements CategoryDao {
    private static final String ADD_CATEGORY = "INSERT INTO categories(category_name,category_description) VALUES(?,?)";
    private static final String FIND_ALL = "SELECT * FROM categories;";
    private static final String FIND_BY_ID = "select category_id,category_name,category_description from categories where category_id=?;";
    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger();
    public JDBCCategoryDao() {
    }
    public JDBCCategoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Category> find(long id) {
        Optional<Category> category = Optional.empty();
        PreparedStatement stmt;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(FIND_BY_ID);
            stmt.setLong(1,id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                category = Optional.of(buildCategory(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return category;
    }

    private Category buildCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setName(rs.getString("category_name"));
        category.setDescription(rs.getString("category_description"));
        category.setId(rs.getInt("category_id"));
        return  category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        Category category;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = connection.prepareStatement(FIND_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setId((int) rs.getLong(1));
                category.setName(rs.getString(2));
                category.setDescription(rs.getString(3));
                list.add(category);

            }
        } catch (SQLException e) {
           LOGGER.error(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return list;

    }

    @Override
    public Category insert(Category category) {
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = connection.prepareStatement(ADD_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                category.setId(rs.getInt(1));
            }
            connection.close();
            return category;
        } catch (SQLException e) {
            LOGGER.error(e);
           throw new CategoryAlreadyExists();
        }finally {
            DBManager.closeConnection(connection);
        }
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public Category delete(Category category, User user) {
        return null;
    }
    public void finish(){
        DBManager.closeConnection(connection);
    }


}
