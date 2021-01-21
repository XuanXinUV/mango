package com.miyou.miyouapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.miyou.miyouapp.mapper")
@SpringBootApplication
@ServletComponentScan("com.miyou.miyouapp.filters")
public class MiyouappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiyouappApplication.class, args);
    }

}
