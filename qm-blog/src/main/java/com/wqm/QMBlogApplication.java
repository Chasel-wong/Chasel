package com.wqm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.wqm.mapper")
@EnableSwagger2
@EnableScheduling
public class QMBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(QMBlogApplication.class,args);
    }
}
