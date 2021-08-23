package com.lst.service;

import com.lst.pojo.Blog;
import com.lst.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:16
 * @Description:
 */
public interface BlogService {


    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable , BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id , Blog blog);

    void deleteBlog(Long id);
}
