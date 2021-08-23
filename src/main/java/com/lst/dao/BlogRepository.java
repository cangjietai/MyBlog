package com.lst.dao;

import com.lst.pojo.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:20
 * @Description:
 */
public interface BlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {

}
