package com.lst.service;

import com.lst.pojo.Comment;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/24 13:11
 * @Description:
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);


    Comment saveComment(Comment comment);
}
