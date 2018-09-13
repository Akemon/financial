package com.hk.manager;

/**
 * @author 何康
 * @date 2018/9/13 9:57
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/***
 * 管理端启动类
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.hk.entity"})
public class ManagerApp {
    public static void main(String[] args){
        SpringApplication.run(ManagerApp.class);
    }
}
