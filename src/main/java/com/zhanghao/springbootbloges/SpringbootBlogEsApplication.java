package com.zhanghao.springbootbloges;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhanghao
 * @create 2021/5/18 16:38
 */
@Slf4j
@SpringBootApplication
public class SpringbootBlogEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogEsApplication.class, args);
        log.info("项目启动成功，访问地址：http://localhost:8082/");
    }

}
