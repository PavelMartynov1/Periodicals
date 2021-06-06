package com.example.Periodicals.model.dao.impl;

import com.example.Periodicals.exceptions.NoMagazineWasFound;
import com.example.Periodicals.model.dao.DBManager;
import com.example.Periodicals.model.dao.MagazineDao;
import com.example.Periodicals.model.entity.Category;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.MagazineSearchParameters;
import com.example.Periodicals.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCMagazineDao implements MagazineDao {
    private final String ADD_MAGAZINE = "INSERT INTO magazines (name,publisher,price,description,periodical_category_id) VALUES(?,?,?,?,?)";

    private static String FIND_ALL_MAGAZINES = "SELECT * FROM magazines join categories on periodical_category_id=category_id;";
    private static String FIND_BY_ID = "SELECT * FROM magazines join categories on periodical_category_id=category_id where magazine_id=?";
    private static String FIND_BY_CATEGORY = "SELECT * FROM magazines join categories on periodical_category_id=category_id WHERE periodical_category_id=?";
    private static String FIND_BY_CATEGORY_PRICE = "SELECT * FROM magazines join categories on periodical_category_id=category_id where periodical_category_id=? order by price %s ;";
    private static String ORDER_BY_PRICE = "SELECT * FROM magazines join categories on periodical_category_id=category_id order by price %s ;";
    private static String SEARCH_BY_NAME = "SELECT * FROM magazines join categories on periodical_category_id=category_id where name LIKE ?";
    private static String FIND_ALL = "SELECT * FROM magazines join categories on periodical_category_id=category_id;";
    private static String GET_MAGAZINE_PRICE = "SELECT price FROM magazines where magazine_id=?";
    private String UPDATE_MAGAZINE = "UPDATE magazines set name=? , description=?,price=?,periodical_category_id=?,publisher=? where magazine_id=?";
    private final String DELETE_MAGAZINE = "DELETE FROM magazines WHERE magazine_id=?";
    private final String MAKE_A_RECORD = "INSERT INTO deleted_magazines (user_id,mag_id,mag_name) values(?,?,?)";
    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger();
    public JDBCMagazineDao() {

    }

    public JDBCMagazineDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Magazine> findByCategoryAndPrice(int id, String price) {
        PreparedStatement stmt;
        ResultSet rs;
        Connection connection = null;
        List<Magazine> list = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(String.format(FIND_BY_CATEGORY_PRICE, price));
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildMagazine(rs));
            }
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage(), e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<Magazine> findByCategory(int id) {
        Connection connection = null;
        PreparedStatement stmt;
        ResultSet rs;
        List<Magazine> list = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(FIND_BY_CATEGORY);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildMagazine(rs));
            }
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            throw new RuntimeException(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<Magazine> orderByPrice(String type) {
        Connection connection = null;
        List<Magazine> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(String.format(ORDER_BY_PRICE, type));
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildMagazine(rs));
            }

        } catch (SQLException e) {
            LOGGER.debug(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<Magazine> nameSearch(String name) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Magazine> list = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(SEARCH_BY_NAME);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildMagazine(rs));
            }
        } catch (SQLException e) {
            LOGGER.debug(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return list;
    }

    public int countAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            connection = DBManager.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(FIND_ALL);
            while (rs.next()) {
                counter++;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return counter;
    }

    @Override
    public int countWithParams(MagazineSearchParameters parameters) {
        Connection connection = null;
        PreparedStatement stmt;
        ResultSet rs;
        int counter = 0;
        String sql=buildSQL(parameters,false);
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(sql);
            setParams(parameters,stmt,false);
            rs = stmt.executeQuery();
            while (rs.next()) {
                counter++;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return counter;
    }

    @Override
    public BigDecimal getPrice(long periodicalId) {
        PreparedStatement stmt;
        ResultSet rs;
        Connection connection = null;
        BigDecimal price = null;
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(GET_MAGAZINE_PRICE);
            stmt.setLong(1, periodicalId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                price = rs.getBigDecimal("price");
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new NoMagazineWasFound(e.getMessage(), e, periodicalId);
        }finally {
            DBManager.closeConnection(connection);
        }
        return price;
    }

    @Override
    public List<Magazine> findWithParameters(MagazineSearchParameters parameters) {
        Connection connection = null;
        PreparedStatement stmt;
        ResultSet rs;
        List<Magazine> list = new ArrayList<>();
        String sql=buildSQL(parameters,true);
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(sql);
            setParams(parameters,stmt,true);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildMagazine(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            DBManager.closeConnection(connection);
        }

        return list;
    }

    private String buildLikeCommand(String name) {
        StringBuilder sb=new StringBuilder(name);
        return sb.insert(0,"%").append("%").toString();
    }

    private void setParams(MagazineSearchParameters parameters,PreparedStatement stmt,boolean limit) throws SQLException {
        int counter=1;
        int rows = parameters.getRecordsPerPage();
        int start = parameters.getCurrentPage() * rows;

            if(parameters.hasCategory()){
                stmt.setString(counter++,parameters.getCategory());
            }
            if(parameters.hasName()){
                stmt.setString(counter++,buildLikeCommand(parameters.getName()));
            }
            if(limit){
                stmt.setInt(counter++,start);
                stmt.setInt(counter,rows);
        }
    }

    private String buildSQL(MagazineSearchParameters parameters, boolean limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM magazines join categories on periodical_category_id=category_id  ");
        boolean isFirst = true;
        if (parameters.hasWhere()) {
            sql.append(" WHERE ");
            if (parameters.hasCategory()) {
                sql.append(" periodical_category_id=?");
                isFirst = false;
            }
            if (parameters.hasName()) {
                if (isFirst) {
                    sql.append(" name LIKE ?");
                } else {
                    sql.append(" and name LIKE ?");
                }
            }
        }
        if (parameters.hasPriceOrder()) {
            sql.append(" order by price ").append(parameters.getPriceOrder());
        }
        if (limit) {
            sql.append(" LIMIT ?,?");
        }
        return sql.toString();
    }

    @Override
    public Optional<Magazine> find(long id) {
        Connection connection = null;
        Magazine magazine = null;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                magazine = buildMagazine(rs);
            }
        } catch (SQLException e) {
            LOGGER.info("magazine with id " + id + " was not found", e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return Optional.ofNullable(magazine);
    }

    @Override
    public List<Magazine> findAll() {
        Connection connection = null;
        List<Magazine> list = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(FIND_ALL_MAGAZINES);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(buildMagazine(rs));
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to find magazines", e);
        }finally {
            DBManager.closeConnection(connection);
        }

        return list;
    }

    @Override
    public Magazine insert(Magazine magazine) {
        Connection connection = null;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            connection = DBManager.getConnection();
            stmt = connection.prepareStatement(ADD_MAGAZINE, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, magazine.getName());
            stmt.setString(2, magazine.getPublisher());
            stmt.setBigDecimal(3, magazine.getPrice());
            stmt.setString(4, magazine.getDescription());
            stmt.setInt(5, magazine.getCategory().getId());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                magazine.setId(rs.getLong(1));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.closeConnection(connection);
        }
        return magazine;
    }

    @Override
    public Magazine update(Magazine magazine) {
        PreparedStatement stmt;
        Connection con = null;
        Magazine updatedMagazine = null;
        try {
            con = DBManager.getConnection();
            stmt = con.prepareStatement(UPDATE_MAGAZINE);
            stmt.setString(1, magazine.getName());
            stmt.setString(2, magazine.getDescription());
            stmt.setBigDecimal(3, magazine.getPrice());
            stmt.setLong(4, magazine.getCategory().getId());
            stmt.setString(5, magazine.getPublisher());
            stmt.setLong(6, magazine.getId());
            updatedMagazine = magazine;
        } catch (SQLException e) {
            LOGGER.error(e);
        }finally {
            DBManager.closeConnection(connection);
        }
        return updatedMagazine;
    }

    @Override
    public Magazine delete(Magazine magazine, User user) {
        Connection connection = null;
        PreparedStatement stmt;
        Magazine deletedMagazine=null;
        try {
            connection = DBManager.getConnection();
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(DELETE_MAGAZINE);
            stmt.setLong(1, magazine.getId());
            if (stmt.executeUpdate() != 0) {
                stmt = connection.prepareStatement(MAKE_A_RECORD);
                stmt.setLong(1, user.getId());
                stmt.setLong(2, magazine.getId());
                stmt.setString(3, magazine.getName());
                stmt.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);
                deletedMagazine=magazine;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            DBManager.rollback(connection);
        }finally {
            DBManager.closeConnection(connection);
        }
        return deletedMagazine;
    }


    public Magazine buildMagazine(ResultSet rs) throws SQLException {

        return new Magazine.Builder().setId(rs.getLong("magazine_id"))
                .setName(rs.getString("name"))
                .setDescription(rs.getString("description"))
                .setCategory(buildCategory(rs))
                .setPrice(rs.getBigDecimal("price"))
                .setPublisher(rs.getString("publisher")).build();

    }

    private Category buildCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("category_id"));
        category.setName(rs.getString("category_name"));
        category.setDescription(rs.getString("category_description"));
        return category;
    }
}
