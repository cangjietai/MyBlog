package com.lst.service;

import com.lst.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:07
 * @Description:
 */
public interface TagService {


    //新增Tag
    Tag saveTag(Tag tag);

    //根据id获取tag
    Tag getTag(Long id);

    //查询全部tags
    List<Tag> listTag();

    //分页查询tag
    Page<Tag> listTag(Pageable pageable);

    //根据名字获取tag
    Tag getTagByName(String name);

    //更新tag
    Tag updateTag(Long id,Tag tag);

    //删除tag
    void deleteTag(Long id);

    //获取tag集合
    List<Tag> listTag(String ids);



}
