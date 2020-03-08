package com.mrfox.prize.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by l0263 on 11/19/16.
 */
@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig {

    @Autowired
    private DataSource druidDataSource;


    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory factory) {
        return new MongoTransactionManager(factory);
    }


    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource);
    }
}
