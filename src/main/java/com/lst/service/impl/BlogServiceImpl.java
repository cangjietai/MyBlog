package com.lst.service.impl;

import com.lst.NotFoundException;
import com.lst.dao.BlogRepository;
import com.lst.pojo.Blog;
import com.lst.pojo.Type;
import com.lst.service.BlogService;
import com.lst.util.MarkdownUtils;
import com.lst.util.MyBeanUtils;
import com.lst.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:20
 * @Description:
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    //使用 SpringBoot JPA封装的方法进行动态组合查询
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id") , blog.getTypeId()));
                }
                if(blog.getRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend") , blog.getRecommend()));
                }
                //list转数组
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }


    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).get();
        if(b ==null){
            throw new NotFoundException("该博客不存在");
        }
        //使用org.springframework.beans.BeanUtils.copyProperties(source, target)方法进行对象间属性的赋值，
        // 将源对象的属性拷贝到目标对象,避免通过get、set方法一个一个属性的赋值
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            //新增时的处理
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);   //设置初始浏览次数为0
        } else {
            //不是新增时只需要更新修改时间
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Page<Blog> listBlog(String query,Pageable pageable){
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listReCommendBlogTop(Integer size) {

        Sort sort= Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable=PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {

        Blog blog=blogRepository.findById(id).get();
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        //希望数据库中的数据还是文本的内容
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    //关联三表查询：t_tags,t_blog,t_blog_tags
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join=root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }


    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=blogRepository.findGroupYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for(String year :years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }


}


