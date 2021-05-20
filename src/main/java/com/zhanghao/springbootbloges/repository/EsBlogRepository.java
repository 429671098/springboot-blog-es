package com.zhanghao.springbootbloges.repository;

import com.zhanghao.springbootbloges.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zhanghao
 * @create 2021/5/18 17:02
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Integer> {
}
