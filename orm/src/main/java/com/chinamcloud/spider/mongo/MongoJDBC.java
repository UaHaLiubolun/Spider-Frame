package com.chinamcloud.spider.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;

public class MongoJDBC {

    private static String host;
    private static int port;
    private static String dataBase;
    private static String userName;
    private static String pw;

    static {
        host = "localhost";
        port = 27018;
        userName = "root";
        dataBase = "spider";
        pw = "";
    }


    static MongoClient mongoClient() {
        if (pw.equals("") || pw == null) {
            return new MongoClient(host, port);
        }
        ServerAddress serverAddress = new ServerAddress(host,port);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential =
                MongoCredential.createCredential(userName, dataBase, pw == null ? null : pw.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs,credentials);
        return mongoClient;
    }


    static MongoDatabase getDataBase(String dataBase) {
        try {
            return mongoClient().getDatabase(dataBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
