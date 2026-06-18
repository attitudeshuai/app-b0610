package com.marathon.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 马拉松配速计算器应用启动类
 */
@SpringBootApplication
public class MarathonCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarathonCalculatorApplication.class, args);
        System.out.println("========================================");
        System.out.println("马拉松配速计算器后端服务启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("健康检查: http://localhost:8080/api/pace/health");
        System.out.println("========================================");
    }
}
