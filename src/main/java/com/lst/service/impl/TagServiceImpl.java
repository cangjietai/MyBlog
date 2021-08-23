package com.lst.service.impl;

import com.lst.dao.TagRepository;
import com.lst.pojo.Tag;
import com.lst.service.TagService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:07
 * @Description:
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    //调用convertToList()方法，将字符串转换成数组
    @Override
    public List<Tag> listTag(String ids) {  //1,2,3
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if((t==null)){
            try {
                throw new NotFoundException("不存在该类型");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }


    //方法的作用：将字符串转化为数组
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids!= null) {
            String[] idarray =ids.split(",");
            for(int i=0; i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


}
