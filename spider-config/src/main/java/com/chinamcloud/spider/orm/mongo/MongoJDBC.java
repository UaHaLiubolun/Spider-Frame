package com.chinamcloud.spider.orm.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoJDBC {

    private static String host;
    private static int port;
    private static String userName;
    private static String pw;

    private static MongoClient client;
    static {
        host = "localhost";
        port = 27017;
        userName = "root";
        pw = "";
    }


    public static MongoClient mongoClient() {
        if (client != null) return client;
        CodecRegistry codecRegistry =  fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                .codecRegistry(codecRegistry)
                .applyToSocketSettings(builder -> builder.connectTimeout(60, TimeUnit.SECONDS))
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60, TimeUnit.SECONDS))
                .build();
        client = MongoClients.create(settings);
        return client;
    }


    public static MongoDatabase getDataBase(String dataBase) {
        try {
            return mongoClient().getDatabase(dataBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
