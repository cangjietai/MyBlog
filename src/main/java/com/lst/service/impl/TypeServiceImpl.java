package com.lst.service.impl;

import com.lst.NotFoundException;
import com.lst.dao.TypeRepository;
import com.lst.pojo.Type;
import com.lst.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 17:08
 * @Description:
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional//分页查询
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    //根据名称查询Type
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    //更新：先查询，再更新
    @Transactional
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).get();
        if ((t ==null)) {
            throw new NotFoundException("不存在该类型");
        }
        //将查询的type复制到查到的对象中
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

}
