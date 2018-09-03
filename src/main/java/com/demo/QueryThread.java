package com.demo;

import com.pojo.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class QueryThread implements Runnable {
    private final Logger LOG = Logger.getLogger(QueryThread.class);
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    private int id;
    private final String sql="select * from user where id=?";
    public QueryThread(Connection connection){
        this.connection = connection;
    }

    public void run() {
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong(1));
                user.setUsername(rs.getString(2));
                Thread.sleep(1000);
               System.out.println( String.format(" =================result:[%s]",user.toString()) );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(null != rs){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(null != preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(null != connection){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
