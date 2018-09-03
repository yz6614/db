package com.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;




public class DruideConnector {
    private static String url = null;

    private static String username = null;

    private static String pwd = null;

    private static DataSource ds_pooled;

    static{
        FileInputStream fileInputStream = null;
        Properties env = new Properties();
        try{
            fileInputStream = new FileInputStream("c3p0.properties");
            env.load(fileInputStream);
            Class.forName(env.getProperty("jdbc.driverClass"));
            url = env.getProperty("jdbc.jdbcUrl");
            username = env.getProperty("jdbc.user");
            pwd = env.getProperty("jdbc.password");
            ds_pooled  = DruidDataSourceFactory.createDataSource(env);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

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
        }
    }

    public void clearup() throws Throwable {
        super.finalize();
    }

}
