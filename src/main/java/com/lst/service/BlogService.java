package com.lst.service;

import com.lst.pojo.Blog;
import com.lst.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:16
 * @Description:
 */
public interface BlogService {


    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id , Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable , BlogQuery blog);

    Page<Blog> listBlog(String query,Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    List<Blog> listReCommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

}
