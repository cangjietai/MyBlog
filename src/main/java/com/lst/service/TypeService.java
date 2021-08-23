package com.lst.service;

import com.lst.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 17:02
 * @Description:
 */
public interface TypeService {

    //新增Type
    Type saveType(Type type);

    //查询Type
    Type getType(Long id);

    //返回全部Type
    List<Type> listType();

    //分页查询Type
    Page<Type>  listType(Pageable pageable);

    //通过名称查询Type
    Type getTypeByName(String name);

    //更新Type
    Type updateType(Long id,Type type);

    //删除Type
    void deleteType(Long id);

}
