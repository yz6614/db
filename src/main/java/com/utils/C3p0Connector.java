package com.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class C3p0Connector {
    private static String url = null;

    private static String username = null;

    private static String pwd = null;

    private static ComboPooledDataSource ds_pooled;

    static{
        Properties env = new Properties();
        try{
            env.load(C3p0Connector.class.getClassLoader().getResourceAsStream("c3p0.properties"));
            url = env.getProperty("jdbc.jdbcUrl");
            username = env.getProperty("jdbc.user");
            pwd = env.getProperty("jdbc.password");
            ds_pooled  =  new ComboPooledDataSource();
            ds_pooled.setUser(username);
            ds_pooled.setPassword(pwd);
            ds_pooled.setJdbcUrl(url);
            ds_pooled.setDriverClass(env.getProperty("jdbc.driverClass"));
//            ds_pooled  =  new ComboPooledDataSource("mysql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try{
            return ds_pooled.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection){
        try{
            if(null != connection){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to release connection!", e);
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
    }

}
