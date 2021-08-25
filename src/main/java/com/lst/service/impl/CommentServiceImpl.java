package com.lst.service.impl;

import com.lst.dao.CommentRepository;
import com.lst.pojo.Comment;
import com.lst.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/24 13:13
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //根据createTime属性正叙排序
        Sort sort= Sort.by("createTime");
        List<Comment> comments=commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            //先获取要回复的对象，再将拿到的对象set到ParentComment中
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            //comment对象没有实例化
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }



    //循环每个顶级的评论节点
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView=new ArrayList<>();
        for(Comment comment: comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并各个子评论到第一级子评论
        combineChildren(commentsView);
        return commentsView;
    }





    private void combineChildren(List<Comment> comments){

        for(Comment comment : comments){
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1){
                recursively(reply1);
            }
            //
            comment.setReplyComments(tempReplys);
            //
            tempReplys = new ArrayList<>();
        }
    }



    private List<Comment> tempReplys = new ArrayList<>();


    private void recursively(Comment comment){
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys=comment.getReplyComments();
            for(Comment reply : replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }

}
