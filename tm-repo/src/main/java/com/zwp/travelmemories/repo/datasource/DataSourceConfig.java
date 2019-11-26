package com.zwp.travelmemories.repo.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * @program: travelmemories
 * @description: 关系数据库配置
 * @author: zwp-flyz
 * @create: 2019-11-26 15:07
 * @version: v1.0
 **/
@Configuration
public class DataSourceConfig {


    // 单druid数据源
    @Bean(value="datasource",initMethod = "init")
    @ConfigurationProperties("tm.datasource")
    public DruidDataSource dataSource(){
        return new DruidDataSource();
    }

    // 事务管理器
    @Bean
    public DataSourceTransactionManager transactionManager(
                        @Qualifier("datasource")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }



}
