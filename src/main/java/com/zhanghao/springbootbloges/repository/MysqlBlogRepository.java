package com.zhanghao.springbootbloges.repository;

import com.zhanghao.springbootbloges.entity.mysql.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zhanghao
 * @create 2021/5/18 17:01
 */
public interface MysqlBlogRepository extends JpaRepository<MysqlBlog, Integer> {
    /**
     * 创建时间倒序查询博客
     *
     * @return
     */
    @Query("select e from MysqlBlog e order by e.createTime desc ")
    List<MysqlBlog> queryAll();

    /**
     * 模糊查询
     *
     * @param keyword
     * @return
     */
    @Query("select e from MysqlBlog e where e.title like concat('%',:keyword,'%') or e.content like concat('%',:keyword,'%')")
    List<MysqlBlog> queryBlog(@Param("keyword") String keyword);
}
