package com.zwp.travelmemories.repo.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @program: travelmemories
 * @description: MyBatis的配置文件
 * @author: zwp-flyz
 * @create: 2019-11-26 15:41
 * @version: v1.0
 **/
@Configuration
@EnableTransactionManagement
@MapperScan("com.zwp.travelmemories.repo.mybatis.mappers")
public class MyBatisConfig {

    // 利用数据源创建会话工厂
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DruidDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }


}
