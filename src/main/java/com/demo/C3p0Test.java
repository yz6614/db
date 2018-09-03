package com.demo;

import com.utils.C3p0Connector;
import com.utils.DruideConnector;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;


import java.sql.Connection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C3p0Test {
    private static Logger logger = Logger.getLogger(C3p0Test.class);
    public static void main(String[] args) {

        System.out.println("缓冲池模拟开始");
        QueryThread queryThread = new QueryThread(connectionFactory(ConnectorPool.C3P0));
        queryThread.setId(1);
        ExecutorService executorService = Executors. newFixedThreadPool(10);
        executorService.execute(queryThread);
        executorService.shutdown();
        System.out.println("缓冲池模拟结束");
    }
    public enum ConnectorPool{
        C3P0,
        DRUIDE
    }
    public static Connection connectionFactory(ConnectorPool connectorPool){
        switch (connectorPool){
            case C3P0:
                return C3p0Connector.getConnection();
            case DRUIDE:
                return DruideConnector.getConnection();
            default:
                return null;
        }
    }
}
