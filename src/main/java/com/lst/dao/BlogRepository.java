package com.lst.dao;

import com.lst.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:20
 * @Description:
 */
public interface BlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);


    //设置每次浏览，浏览次数自动+1
    @Transactional
    @Modifying
    @Query("update Blog b set b.views=b.views+1 where b.id= ?1")
    int updateViews(Long id);

    //根据博客发布年份排序
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc")
    List<String> findGroupYear();

    //根据年份拿到该年份对应的blog
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y')=?1 ")
    List<Blog> findByYear(String year);

}
