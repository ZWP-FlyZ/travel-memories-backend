package com.zwp.travelmemories.repo;

import com.zwp.travelmemories.repo.mybatis.mappers.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TmRepoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TmRepoApplication.class, args);
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    UserMapper userMapper;

    @Override
    public void run(String... args) throws Exception {
//        System.err.println(userMapper.selectUserByUsername("zwp"));
    }
}
