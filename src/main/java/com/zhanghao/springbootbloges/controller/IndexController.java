package com.zhanghao.springbootbloges.controller;

import com.zhanghao.springbootbloges.entity.mysql.MysqlBlog;
import com.zhanghao.springbootbloges.repository.MysqlBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author zhanghao
 * @create 2021/5/18 17:05
 */
@Controller
@Slf4j
public class IndexController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @GetMapping("/")
    public String index() {
        List<MysqlBlog> all = mysqlBlogRepository.findAll();
        log.info("【查询所有的博客数据】all={}", all.size());
        return "index.html";
    }
}

