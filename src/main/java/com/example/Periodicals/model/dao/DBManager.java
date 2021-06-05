package com.example.Periodicals.model.dao;



import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static DataSource ds;
    private static final Logger LOGGER = LogManager.getLogger();
    private DBManager() {

    }

    public static Connection getConnection() throws SQLException {
        if(ds == null) {
            try {
                Context context = new InitialContext();
                ds = (DataSource) context.lookup("java:comp/env/jdbc/periodicals");
            } catch (NamingException e) {
                LOGGER.log(Level.ERROR,e.getMessage());
            }
        }
        return ds.getConnection();
    }

    public static void closeConnection(Connection con) {
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR,e);
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void rollback(Connection con) {
        if(con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR,e);
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
