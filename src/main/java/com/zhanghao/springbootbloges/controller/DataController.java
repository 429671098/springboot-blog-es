package com.zhanghao.springbootbloges.controller;

import com.zhanghao.springbootbloges.entity.es.EsBlog;
import com.zhanghao.springbootbloges.entity.mysql.MysqlBlog;
import com.zhanghao.springbootbloges.repository.EsBlogRepository;
import com.zhanghao.springbootbloges.repository.MysqlBlogRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.StopWatch;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhanghao
 * @create 2021/5/18 17:07
 */
@RestController
@Slf4j
public class DataController {
    private static final String MYSQL = "mysql";
    private static final String ES = "es";

    @Autowired
    MysqlBlogRepository mysqlBlogRepository;
    @Autowired
    EsBlogRepository esBlogRepository;
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("/blogs")
    public Object blogList() {
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        Map<String, Object> map = new HashMap<>();
        // 统计耗时
        StopWatch watch = new StopWatch();
        watch.start();
        String type = param.getType();
        if(MYSQL.equals(type)){
            // mysql 的搜索
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlog(param.getKeyword());
            map.put("list", mysqlBlogs);
        } else if(ES.equals(type)){
            // es 的搜索
//            NativeSearchQueryBuilder ：用于建造一个NativeSearchQuery查询对象；
//            NativeSearchQuery ：是springdata中的查询条件；
//            QueryBuilders ：设置查询条件，是ES中的类；

            // 搜索源构建对象
            NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
            NativeSearchQuery query = builder.withQuery(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchPhraseQuery("title", param.getKeyword()))
                    .should(QueryBuilders.matchPhraseQuery("content", param.getKeyword())))
                    .withPageable(PageRequest.of(0,5))
                    .build();

            SearchHits<EsBlog> searchHits = elasticsearchRestTemplate.search(query, EsBlog.class);
            List<EsBlog> esBlogList = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
            map.put("list", esBlogList); // 内容
//            map.put("count", searchHits.getTotalHits()); // 总条数
        }else {
            return "你要啥呢小老弟";
        }
        watch.stop();
        // 计算耗时
        long millis = watch.totalTime().getMillis();
        map.put("duration", millis);
        return map;
    }

    @GetMapping("/blog/{id}")
    public Object blog(@PathVariable Integer id){
        Optional<MysqlBlog> byId = mysqlBlogRepository.findById(id);
        return byId.get();
    }



    @Data
    private static class Param {
        private String type;
        private String keyword;
    }




















}
