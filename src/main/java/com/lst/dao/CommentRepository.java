package com.lst.dao;

import com.lst.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/24 13:14
 * @Description:
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {


    //根据最新的创建时间，倒叙排序
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
