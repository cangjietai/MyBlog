package com.lst.service.impl;

import com.lst.NotFoundException;
import com.lst.dao.BlogRepository;
import com.lst.pojo.Blog;
import com.lst.pojo.Type;
import com.lst.service.BlogService;
import com.lst.util.MyBeanUtils;
import com.lst.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
